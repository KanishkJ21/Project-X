package com.agrihelp.controller;

import com.agrihelp.model.CropRecommendation;
import com.agrihelp.service.CropRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropRecommendationController {

    @Autowired
    private CropRecommendationService cropService;

    // Get recommendation for a field (accurate if SHC exists)
    @GetMapping("/recommend/{fieldName}")
    public CropRecommendation getCropRecommendation(@PathVariable String fieldName) {
        return cropService.getCropRecommendation(fieldName);
    }

    // General recommendation by region (if SHC skipped)
    @GetMapping("/region/{region}")
    public CropRecommendation getByRegion(@PathVariable String region) {
        return cropService.getGeneralCropRecommendationByRegion(region);
    }

    // Admin: save crop recommendation
    @PostMapping("/save")
    public CropRecommendation saveCrop(@RequestBody CropRecommendation recommendation) {
        return cropService.saveCropRecommendation(recommendation);
    }

    // Admin: view all
    @GetMapping("/all")
    public List<CropRecommendation> getAllCrops() {
        return cropService.getAllCropRecommendations();
    }
}
