package com.agrihelp.controller;

import com.agrihelp.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/analytics/soilReport")
    public String getSoilReport() {
        return analyticsService.generateSoilReport();
    }

    @GetMapping("/analytics/averagePh")
    public double getAveragePh() {
        return analyticsService.getAveragePh();
    }

    @GetMapping("/analytics/averageMoisture")
    public double getAverageMoisture() {
        return analyticsService.getAverageMoisture();
    }

    @GetMapping("/analytics/averageNitrogen")
    public double getAverageNitrogen() {
        return analyticsService.getAverageNitrogen();
    }
}
