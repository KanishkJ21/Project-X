package com.agrihelp.service;

import com.agrihelp.model.Crop;
import com.agrihelp.model.SoilData;
import com.agrihelp.repository.CropRepository;
import com.agrihelp.repository.SoilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private SoilRepository soilRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private SHCService shcService; // ✅ use SHC first, fallback to soilData

    /**
     * Recommend crops for a user.
     * Priority:
     *  1. Use SHC values (if present).
     *  2. Else use SoilData (manual entry).
     *  3. Else fallback to regional averages.
     */
    public List<Crop> recommendCrops(String userId, String soilId, String city) {
        Map<String, Double> soilMap = new HashMap<>();

        // First try SHC or regional fallback
        soilMap = shcService.getSoilMapForUser(userId);

        // If no SHC/regional data and soilId provided → use SoilData
        if ((soilMap == null || soilMap.isEmpty()) && soilId != null) {
            SoilData soil = soilRepository.findById(soilId).orElse(null);
            if (soil != null) {
                soilMap.put("ph", soil.getPh());
                soilMap.put("nitrogen", soil.getNitrogen());
                soilMap.put("phosphorus", soil.getPhosphorus());
                soilMap.put("potassium", soil.getPotassium());
            }
        }

        if (soilMap.isEmpty()) {
            return new ArrayList<>(); // ❌ No soil info available
        }

        double ph = soilMap.getOrDefault("ph", 6.5); // assume neutral if missing

        // Fetch weather (rainfall)
        Map<String, Object> weatherData = weatherService.getWeatherByCity(city);
        double rainfall = 0.0;
        if (weatherData != null && weatherData.get("rainfall") instanceof Number) {
            rainfall = ((Number) weatherData.get("rainfall")).doubleValue();
        }

        // ✅ Use repository queries instead of manual loop
        List<Crop> recommended = cropRepository
                .findByMinPHLessThanEqualAndMaxPHGreaterThanEqualAndMinRainfallLessThanEqualAndMaxRainfallGreaterThanEqual(
                        ph, ph, rainfall, rainfall
                );

        return recommended;
    }

    /**
     * Fetch info for a single crop (for SustainabilityController).
     */
    public Map<String, Object> getCropInfo(String cropName) {
        Map<String, Object> cropInfo = new HashMap<>();
        cropRepository.findByName(cropName).ifPresentOrElse(crop -> {
            cropInfo.put("minPH", crop.getMinPH());
            cropInfo.put("maxPH", crop.getMaxPH());
            cropInfo.put("minRainfall", crop.getMinRainfall());
            cropInfo.put("maxRainfall", crop.getMaxRainfall());
            cropInfo.put("sowingTime", crop.getSowingTime()); // ✅ use actual model field
        }, () -> {
            cropInfo.put("minPH", "N/A");
            cropInfo.put("maxPH", "N/A");
            cropInfo.put("minRainfall", "N/A");
            cropInfo.put("maxRainfall", "N/A");
            cropInfo.put("sowingTime", "N/A");
        });
        return cropInfo;
    }
}
