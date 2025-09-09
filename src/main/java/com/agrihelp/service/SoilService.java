package com.agrihelp.service;

import com.agrihelp.model.SoilData;
import com.agrihelp.repository.SoilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoilService {

    @Autowired
    private SoilRepository soilRepository;

    // -------------------- CRUD --------------------
    public List<SoilData> getAllSoilData() {
        return soilRepository.findAll();
    }

    public Optional<SoilData> getSoilDataById(String id) {
        return soilRepository.findById(id);
    }

    public SoilData addSoilData(SoilData soilData) {
        return soilRepository.save(soilData);
    }

    public SoilData updateSoilData(String id, SoilData updatedData) {
        return soilRepository.findById(id).map(soil -> {
            soil.setFieldName(updatedData.getFieldName());
            soil.setPh(updatedData.getPh());
            soil.setNitrogen(updatedData.getNitrogen());
            soil.setPhosphorus(updatedData.getPhosphorus());
            soil.setPotassium(updatedData.getPotassium());
            soil.setMoisture(updatedData.getMoisture());
            // ✅ Add extended attributes if present
            soil.setUserId(updatedData.getUserId());
            soil.setState(updatedData.getState());
            soil.setDistrict(updatedData.getDistrict());
            return soilRepository.save(soil);
        }).orElseThrow(() -> new RuntimeException("SoilData not found with id " + id));
    }

    public void deleteSoilData(String id) {
        soilRepository.deleteById(id);
    }

    // -------------------- Extended Methods --------------------

    public Optional<SoilData> getByFieldName(String fieldName) {
        return soilRepository.findByFieldName(fieldName);
    }

    public List<SoilData> getByUserId(String userId) {
        return soilRepository.findByUserId(userId);
    }

    public List<SoilData> getByStateAndDistrict(String state, String district) {
        return soilRepository.findByStateAndDistrict(state, district);
    }

    public List<SoilData> getByState(String state) {
        return soilRepository.findByState(state);
    }
}
