package com.agrihelp.service;

import com.agrihelp.model.SoilData;
import com.agrihelp.repository.SoilDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoilDataService {

    @Autowired
    private SoilDataRepository soilDataRepository;

    /**
     * Save soil data for a farmer (from SHC or manual input)
     */
    public SoilData saveSoilData(SoilData soilData) {
        return soilDataRepository.save(soilData);
    }

    /**
     * Get soil data by unique field name
     */
    public Optional<SoilData> getSoilDataByFieldName(String fieldName) {
        return soilDataRepository.findByFieldNameIgnoreCase(fieldName);
    }

    /**
     * Controller-friendly method: Get soil data by field name
     */
    public SoilData getSoilData(String fieldName) {
        return getSoilDataByFieldName(fieldName).orElse(null);
    }

    /**
     * Get soil data for a specific region (used when SHC is skipped)
     */
    public List<SoilData> getSoilDataByRegion(String region) {
        return soilDataRepository.findByRegionIgnoreCase(region);
    }

    /**
     * Get all soil data (for admin/analytics use)
     */
    public List<SoilData> getAllSoilData() {
        return soilDataRepository.findAll();
    }

    /**
     * Delete soil data for a specific field
     */
    public void deleteSoilData(String fieldId) {
        soilDataRepository.deleteById(fieldId);
    }
}
