package com.agrihelp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    private final String API_KEY = "YOUR_API_KEY";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

    private final RestTemplate restTemplate = new RestTemplate();

    // Generic method to fetch weather data as Map for any feature
    @SuppressWarnings("unchecked")
    public Map<String, Object> getWeatherByCity(String city) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", city);
        uriVariables.put("apiKey", API_KEY);

        Map<String, Object> response = restTemplate.getForObject(BASE_URL, Map.class, uriVariables);

        // Returning only required parts to features
        Map<String, Object> weatherData = new HashMap<>();
        if (response != null) {
            Map<String, Object> main = (Map<String, Object>) response.get("main");
            Map<String, Object> wind = (Map<String, Object>) response.get("wind");

            weatherData.put("temperature", main.get("temp"));
            weatherData.put("humidity", main.get("humidity"));
            weatherData.put("windSpeed", wind.get("speed"));
            weatherData.put("city", response.get("name"));

            // If rainfall info exists
            Map<String, Object> rain = (Map<String, Object>) response.get("rain");
            if (rain != null && rain.containsKey("1h")) {
                weatherData.put("rainfall", rain.get("1h"));
            } else {
                weatherData.put("rainfall", 0);
            }
        }

        return weatherData;
    }

    // Feature-specific helper for Crop
    public Map<String, Object> getWeatherForCrop(String city) {
        Map<String, Object> data = getWeatherByCity(city);
        Map<String, Object> cropWeather = new HashMap<>();
        cropWeather.put("temperature", data.get("temperature"));
        cropWeather.put("humidity", data.get("humidity"));
        cropWeather.put("city", data.get("city"));
        return cropWeather;
    }

    // Feature-specific helper for Irrigation
    public Map<String, Object> getWeatherForIrrigation(String city) {
        Map<String, Object> data = getWeatherByCity(city);
        Map<String, Object> irrigationWeather = new HashMap<>();
        irrigationWeather.put("temperature", data.get("temperature"));
        irrigationWeather.put("humidity", data.get("humidity"));
        irrigationWeather.put("rainfall", data.get("rainfall"));
        irrigationWeather.put("city", data.get("city"));
        return irrigationWeather;
    }
}
