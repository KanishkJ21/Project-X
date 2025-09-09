package com.agrihelp.service;

import com.agrihelp.model.SoilData;
import com.agrihelp.repository.SoilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private SoilRepository soilRepository;

    // Average pH
    public double getAveragePh() {
        List<SoilData> soils = soilRepository.findAll();
        return soils.stream()
                .mapToDouble(SoilData::getPh)  // ensure SoilData has getPh()
                .average()
                .orElse(0.0);
    }

    // Average Moisture
    public double getAverageMoisture() {
        List<SoilData> soils = soilRepository.findAll();
        return soils.stream()
                .mapToDouble(SoilData::getMoisture) // ensure SoilData has getMoisture()
                .average()
                .orElse(0.0);
    }

    // Average Nitrogen
    public double getAverageNitrogen() {
        List<SoilData> soils = soilRepository.findAll();
        return soils.stream()
                .mapToDouble(SoilData::getNitrogen) // ensure SoilData has getNitrogen()
                .average()
                .orElse(0.0);
    }

    // Optional: Generate a simple soil report
    public String generateSoilReport() {
        return "Average pH: " + getAveragePh() +
                ", Average Moisture: " + getAverageMoisture() +
                ", Average Nitrogen: " + getAverageNitrogen();
    }
}
