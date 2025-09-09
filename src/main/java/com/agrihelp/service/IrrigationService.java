package com.agrihelp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IrrigationService {

    private final WeatherService weatherService;

    public IrrigationService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Generates irrigation recommendations based on crop type and current weather.
     * @param cropType the crop for which irrigation info is needed
     * @param city the city/location for weather data
     * @return a map containing irrigation recommendations
     */
    public Map<String, Object> getIrrigationRecommendation(String cropType, String city) {
        Map<String, Object> recommendation = new HashMap<>();

        // Fetch real-time weather data
        Map<String, Object> weatherData = weatherService.getWeatherByCity(city);

        double rainfall = 0;
        if (weatherData.containsKey("rainfall")) {
            rainfall = ((Number) weatherData.get("rainfall")).doubleValue();
        }

        // Simple logic for irrigation recommendation based on rainfall
        String irrigationAdvice;
        if (rainfall > 20) {
            irrigationAdvice = "No irrigation needed. Recent heavy rainfall.";
        } else if (rainfall > 5) {
            irrigationAdvice = "Light irrigation recommended.";
        } else {
            irrigationAdvice = "Regular irrigation needed.";
        }

        recommendation.put("crop", cropType);
        recommendation.put("city", city);
        recommendation.put("rainfall", rainfall);
        recommendation.put("advice", irrigationAdvice);

        return recommendation;
    }
}
