package com.agrihelp.controller;

import com.agrihelp.model.Fertilizer;
import com.agrihelp.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fertilizer")
public class FertilizerController {

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * Suggest fertilizers for a crop.
     * - If userId is provided → use SHC values if available.
     * - Else fallback to soilId-based values.
     */
    @GetMapping("/suggest")
    public List<Fertilizer> suggestFertilizers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String soilId,
            @RequestParam String cropType) {
        return fertilizerService.suggestFertilizers(userId, soilId, cropType);
    }

    /**
     * Get fertilizer recommendation for a specific crop & soil type
     * (used for sustainability insights).
     */
    @GetMapping("/recommendation")
    public Map<String, Object> getRecommendation(
            @RequestParam String cropName,
            @RequestParam String soilType) {
        return fertilizerService.getFertilizerRecommendation(cropName, soilType);
    }
}
