package com.agrihelp.controller;

import com.agrihelp.model.SoilData;
import com.agrihelp.service.SoilDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soil")
public class SoilDataController {

    @Autowired
    private SoilDataService soilService;

    // Save soil data
    @PostMapping("/save")
    public SoilData saveSoilData(@RequestBody SoilData soilData) {
        return soilService.saveSoilData(soilData);
    }

    // Get soil data for a farmer's field
    @GetMapping("/{fieldName}")
    public SoilData getSoilData(@PathVariable String fieldName) {
        return soilService.getSoilData(fieldName);
    }

    // Get all soil data (admin)
    @GetMapping("/all")
    public List<SoilData> getAllSoilData() {
        return soilService.getAllSoilData();
    }
}
