package com.agrihelp.controller;

import com.agrihelp.service.CropService;
import com.agrihelp.service.FertilizerService;
import com.agrihelp.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sustainability")
public class SustainabilityController {

    private final WeatherService weatherService;
    private final CropService cropService;
    private final FertilizerService fertilizerService;

    public SustainabilityController(WeatherService weatherService,
                                    CropService cropService,
                                    FertilizerService fertilizerService) {
        this.weatherService = weatherService;
        this.cropService = cropService;
        this.fertilizerService = fertilizerService;
    }

    @GetMapping("/tips")
    public Map<String, Object> getSustainabilityTips(@RequestParam String city,
                                                     @RequestParam String cropName,
                                                     @RequestParam String soilType) {
        Map<String, Object> tips = new HashMap<>();

        // --- General static tips ---
        tips.put("tip1", "Use organic fertilizers.");
        tips.put("tip2", "Practice crop rotation.");
        tips.put("tip3", "Harvest rainwater for irrigation.");

        // --- Personalized weather-based tip ---
        Map<String, Object> weatherData = weatherService.getWeatherByCity(city);
        if (weatherData != null) {
            double rainfall = Double.parseDouble(weatherData.getOrDefault("rainfall", "0").toString());
            if (rainfall < 10) {
                tips.put("weatherTip", "Low rainfall expected. Consider water-saving irrigation methods.");
            } else {
                tips.put("weatherTip", "Sufficient rainfall expected. You can reduce irrigation frequency.");
            }
        }

        // --- Crop-specific tip ---
        Map<String, Object> cropInfo = cropService.getCropInfo(cropName);
        if (cropInfo != null) {
            tips.put("cropTip", "Recommended sowing time for " + cropName + ": " + cropInfo.getOrDefault("sowingTime", "N/A"));
        }

        // --- Soil-specific fertilizer tip ---
        Map<String, Object> fertilizerTip = fertilizerService.getFertilizerRecommendation(cropName, soilType);
        if (fertilizerTip != null) {
            tips.put("fertilizerTip", "Recommended fertilizer: " + fertilizerTip.getOrDefault("fertilizer", "N/A"));
        }

        return tips;
    }
}
