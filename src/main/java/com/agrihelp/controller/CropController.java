package com.agrihelp.controller;

import com.agrihelp.model.Crop;
import com.agrihelp.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crop")
public class CropController {

    @Autowired
    private CropService cropService;

    /**
     * Recommend crops for a user.
     * Priority:
     *  1. Uses SHC data if available
     *  2. Else uses SoilData if soilId provided
     *  3. Else falls back to regional averages
     */
    @GetMapping("/recommend")
    public List<Crop> recommendCrops(
            @RequestParam String userId,
            @RequestParam(required = false) String soilId,
            @RequestParam String city) {

        return cropService.recommendCrops(userId, soilId, city);
    }

    /**
     * Fetch details for a specific crop.
     */
    @GetMapping("/info")
    public Object getCropInfo(@RequestParam String cropName) {
        return cropService.getCropInfo(cropName);
    }
}
