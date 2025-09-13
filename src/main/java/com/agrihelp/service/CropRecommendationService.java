package com.agrihelp.service;

import com.agrihelp.model.CropRecommendation;
import com.agrihelp.model.SoilData;
import com.agrihelp.repository.CropRecommendationRepository;
import com.agrihelp.repository.SoilDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropRecommendationService {

    @Autowired
    private CropRecommendationRepository cropRepository;

    @Autowired
    private SoilDataRepository soilDataRepository;

    /**
     * Get crop recommendation based on field's soil data
     */
    public CropRecommendation getCropRecommendation(String fieldName) {
        Optional<SoilData> soilOpt = soilDataRepository.findByFieldNameIgnoreCase(fieldName);

        if (soilOpt.isPresent()) {
            SoilData soil = soilOpt.get();

            // Find crops matching soil type and pH range
            List<CropRecommendation> matches = cropRepository.findBySoilTypeIgnoreCaseAndPhBetween(
                    soil.getSoilType(), soil.getPh() - 0.5, soil.getPh() + 0.5
            );

            if (!matches.isEmpty()) {
                CropRecommendation rec = matches.get(0);
                rec.setBasedOnSoilData(true);
                rec.setReason("Recommendation based on soil data from field: " + fieldName);
                return rec;
            }

            // If no match, fallback to region-based
            return getGeneralCropRecommendationByRegion(soil.getRegion());
        }

        // If no soil data available → fallback to general recommendation
        return getGeneralCropRecommendationByRegion("Unknown");
    }

    /**
     * Get general crop recommendation by region (approximate, if SHC skipped)
     */
    public CropRecommendation getGeneralCropRecommendationByRegion(String region) {
        List<CropRecommendation> regionalCrops = cropRepository.findByRegionIgnoreCase(region);

        if (!regionalCrops.isEmpty()) {
            CropRecommendation rec = regionalCrops.get(0);
            rec.setBasedOnSoilData(false); // mark as approximate
            rec.setReason("General recommendation based on region/season.");
            return rec;
        }

        // fallback: default crop
        return CropRecommendation.builder()
                .recommendedCrop("Wheat")
                .reason("Default crop recommendation for general use.")
                .basedOnSoilData(false)
                .build();
    }

    /**
     * Save a new crop recommendation (admin use)
     */
    public CropRecommendation saveCropRecommendation(CropRecommendation recommendation) {
        return cropRepository.save(recommendation);
    }

    /**
     * Get all crop recommendations (admin view)
     */
    public List<CropRecommendation> getAllCropRecommendations() {
        return cropRepository.findAll();
    }
}
