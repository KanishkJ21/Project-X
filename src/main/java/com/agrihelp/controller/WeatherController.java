package com.agrihelp.controller;

import com.agrihelp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // Generic endpoint for any feature
    @GetMapping("/{city}")
    public Map<String, Object> getWeather(@PathVariable String city) {
        return weatherService.getWeatherByCity(city);
    }

    // Crop-specific endpoint
    @GetMapping("/crop/{city}")
    public Map<String, Object> getWeatherForCrop(@PathVariable String city) {
        return weatherService.getWeatherForCrop(city);
    }

    // Irrigation-specific endpoint
    @GetMapping("/irrigation/{city}")
    public Map<String, Object> getWeatherForIrrigation(@PathVariable String city) {
        return weatherService.getWeatherForIrrigation(city);
    }
}
