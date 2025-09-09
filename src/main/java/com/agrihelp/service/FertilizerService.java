package com.agrihelp.service;

import com.agrihelp.model.Fertilizer;
import com.agrihelp.model.SHC;
import com.agrihelp.model.SoilData;
import com.agrihelp.repository.FertilizerRepository;
import com.agrihelp.repository.SHCRepository;
import com.agrihelp.repository.SoilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FertilizerService {

    @Autowired
    private FertilizerRepository fertilizerRepository;

    @Autowired
    private SoilRepository soilRepository;

    @Autowired
    private SHCRepository shcRepository;

    /**
     * Suggest fertilizers based on SHC (preferred) or fallback to SoilData.
     */
    public List<Fertilizer> suggestFertilizers(String userId, String soilId, String cropType) {
        Double nitrogen = null, phosphorus = null, potassium = null;

        // ✅ 1. Try SHC first
        Optional<SHC> shcOpt = shcRepository.findByUserId(userId);
        if (shcOpt.isPresent()) {
            SHC shc = shcOpt.get();
            nitrogen = shc.getNitrogen();
            phosphorus = shc.getPhosphorus();
            potassium = shc.getPotassium();
        } else if (soilId != null) {
            // ✅ 2. Fallback: use soil data
            SoilData soil = soilRepository.findById(soilId).orElse(null);
            if (soil != null) {
                nitrogen = soil.getNitrogen();
                phosphorus = soil.getPhosphorus();
                potassium = soil.getPotassium();
            }
        }

        // ✅ 3. If no data, return empty list
        if (nitrogen == null && phosphorus == null && potassium == null) {
            return new ArrayList<>();
        }

        // ✅ 4. Query repository by nutrient gaps + cropType
        List<Fertilizer> suggestions = new ArrayList<>();
        for (Fertilizer fert : fertilizerRepository.findByCropType(cropType)) {
            boolean needsN = nitrogen != null && nitrogen < fert.getRecommendedN();
            boolean needsP = phosphorus != null && phosphorus < fert.getRecommendedP();
            boolean needsK = potassium != null && potassium < fert.getRecommendedK();

            if (needsN || needsP || needsK) {
                suggestions.add(fert);
            }
        }

        return suggestions;
    }

    /**
     * Get fertilizer recommendation for crop & soil type (simple sustainability info).
     */
    public Map<String, Object> getFertilizerRecommendation(String cropName, String soilType) {
        Map<String, Object> recommendation = new HashMap<>();

        List<Fertilizer> fertilizers = new ArrayList<>();
        for (Fertilizer fert : fertilizerRepository.findByCropType(cropName)) {
            if (fert.getSoilType().equalsIgnoreCase(soilType)) {
                fertilizers.add(fert);
            }
        }

        if (!fertilizers.isEmpty()) {
            Fertilizer fert = fertilizers.get(0); // pick first match
            recommendation.put("fertilizer", fert.getName());
            recommendation.put("N", fert.getRecommendedN());
            recommendation.put("P", fert.getRecommendedP());
            recommendation.put("K", fert.getRecommendedK());
        } else {
            recommendation.put("fertilizer", "N/A");
        }

        return recommendation;
    }
}
