package com.agrihelp.controller;

import com.agrihelp.service.IrrigationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/irrigation")
public class IrrigationController {

    private final IrrigationService irrigationService;

    public IrrigationController(IrrigationService irrigationService) {
        this.irrigationService = irrigationService;
    }

    /**
     * Endpoint to get irrigation recommendation for a crop at a city.
     * @param cropType the crop type
     * @param city the city/location
     * @return irrigation recommendation
     */
    @GetMapping("/recommendation")
    public ResponseEntity<Map<String, Object>> getRecommendation(
            @RequestParam String cropType,
            @RequestParam String city) {

        Map<String, Object> recommendation = irrigationService.getIrrigationRecommendation(cropType, city);
        return ResponseEntity.ok(recommendation);
    }
}
