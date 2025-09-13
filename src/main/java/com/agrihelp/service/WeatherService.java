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
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    private final String API_KEY = "YOUR_API_KEY";  // Replace with your API key
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherData getWeatherByCity(String city) {
        // 1. Check cached weather in DB
        Optional<WeatherData> cachedWeather = weatherDataRepository.findByCityIgnoreCase(city);

        if (cachedWeather.isPresent()) {
            WeatherData weather = cachedWeather.get();

            // Cache is valid for 30 minutes
            if (weather.getLastUpdated() != null &&
                weather.getLastUpdated().isAfter(LocalDateTime.now().minusMinutes(30))) {
                return weather;
            }
        }

        // 2. Fetch from API
        RestTemplate restTemplate = new RestTemplate();
        try {
            // Fetch raw JSON as String
            String jsonResponse = restTemplate.getForObject(BASE_URL, String.class, city, API_KEY);

            // Convert JSON string to Map
            Map<String, Object> response = objectMapper.readValue(jsonResponse,
                    new TypeReference<Map<String, Object>>() {});

            // Parse "main" into MainData
            MainData main = objectMapper.convertValue(response.get("main"), MainData.class);
            double temperature = main != null ? main.getTemp() : 0.0;
            double humidity = main != null ? main.getHumidity() : 0.0;

            // Parse "weather" into List<WeatherCondition>
            List<WeatherCondition> weatherList = objectMapper.convertValue(
                    response.get("weather"), new TypeReference<List<WeatherCondition>>() {}
            );
            String condition = (weatherList != null && !weatherList.isEmpty())
                    ? weatherList.get(0).getMain()
                    : "Unknown";

            // Build WeatherData
            WeatherData newWeather = WeatherData.builder()
                    .city(city)
                    .temperature(temperature)
                    .humidity(humidity)
                    .condition(condition)
                    .lastUpdated(LocalDateTime.now())
                    .build();

            weatherDataRepository.save(newWeather); // Save cache
            return newWeather;

        } catch (Exception e) {
            // Fallback to cached data if API fails
            if (cachedWeather.isPresent()) {
                return cachedWeather.get();
            }
            throw new RuntimeException("Unable to fetch weather data for " + city, e);
        }
    }
}
