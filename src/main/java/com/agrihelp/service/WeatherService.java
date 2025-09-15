package com.agrihelp.service;

import com.agrihelp.model.MainData;
import com.agrihelp.model.WeatherCondition;
import com.agrihelp.model.WeatherData;
import com.agrihelp.repository.WeatherDataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private ApiRetryUtil apiRetryUtil;  // ✅ Retry utility

    private final String API_KEY = "YOUR_API_KEY";  // Replace with your OpenWeatherMap API key
    private final String BASE_URL =
            "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int CACHE_VALIDITY_MINUTES = 30;

    /**
     * Get weather data for a city.
     * Uses cached data if available (valid for 30 mins), otherwise fetches from API with retries.
     */
    public WeatherData getWeatherByCity(String city) {
        // 1️⃣ Check cached weather in DB
        Optional<WeatherData> cachedWeather = weatherDataRepository.findByCityIgnoreCase(city);

        if (cachedWeather.isPresent()) {
            WeatherData weather = cachedWeather.get();

            // ✅ Cache is valid for 30 minutes
            if (weather.getLastUpdated() != null &&
                    weather.getLastUpdated().isAfter(LocalDateTime.now().minusMinutes(CACHE_VALIDITY_MINUTES))) {
                return weather;
            }
        }

        // 2️⃣ Fetch from API with retries
        RestTemplate restTemplate = new RestTemplate();
        try {
            String jsonResponse = apiRetryUtil.getForObjectWithRetry(restTemplate, BASE_URL, String.class, city, API_KEY);

            // Convert JSON string to Map
            Map<String, Object> response = objectMapper.readValue(jsonResponse,
                    new TypeReference<Map<String, Object>>() {});

            // Parse "main" into MainData
            MainData main = objectMapper.convertValue(response.get("main"), MainData.class);
            double temperature = (main != null) ? main.getTemp() : 0.0;
            double humidity = (main != null) ? main.getHumidity() : 0.0;

            // Parse "weather" into List<WeatherCondition>
            List<WeatherCondition> weatherList = objectMapper.convertValue(
                    response.get("weather"), new TypeReference<List<WeatherCondition>>() {});
            String condition = (weatherList != null && !weatherList.isEmpty())
                    ? weatherList.get(0).getMain()
                    : "Unknown";

            // Build new WeatherData
            WeatherData newWeather = WeatherData.builder()
                    .city(city)
                    .temperature(temperature)
                    .humidity(humidity)
                    .condition(condition)
                    .lastUpdated(LocalDateTime.now())
                    .build();

            // ✅ Save only if new data differs from last cache
            if (cachedWeather.isEmpty() ||
                    !Objects.equals(cachedWeather.get().getTemperature(), newWeather.getTemperature()) ||
                    !Objects.equals(cachedWeather.get().getCondition(), newWeather.getCondition())) {
                weatherDataRepository.save(newWeather);
            }

            return newWeather;

        } catch (Exception e) {
            e.printStackTrace();

            // ✅ Fallback to cached data if API fails
            if (cachedWeather.isPresent()) {
                return cachedWeather.get();
            }

            // ✅ If no cache exists, return placeholder data
            return WeatherData.builder()
                    .city(city)
                    .temperature(0.0)
                    .humidity(0.0)
                    .condition("Unavailable")
                    .lastUpdated(LocalDateTime.now())
                    .build();
        }
    }
}
