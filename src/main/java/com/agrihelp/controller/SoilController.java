package com.agrihelp.controller;

import com.agrihelp.model.SoilData;
import com.agrihelp.service.SoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/soil")
public class SoilController {

    @Autowired
    private SoilService soilService;

    // ----------------- CRUD -----------------
    @GetMapping("/getAll")
    public List<SoilData> getAllSoilData() {
        return soilService.getAllSoilData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSoilById(@PathVariable String id) {
        Optional<SoilData> soil = soilService.getSoilDataById(id);
        return soil.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public SoilData addSoil(@RequestBody SoilData soilData) {
        return soilService.addSoilData(soilData);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSoil(@PathVariable String id, @RequestBody SoilData soilData) {
        try {
            return ResponseEntity.ok(soilService.updateSoilData(id, soilData));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSoil(@PathVariable String id) {
        soilService.deleteSoilData(id);
        return ResponseEntity.ok().build();
    }

    // ----------------- Extended Endpoints -----------------

    @GetMapping("/field/{fieldName}")
    public ResponseEntity<?> getByFieldName(@PathVariable String fieldName) {
        Optional<SoilData> soil = soilService.getByFieldName(fieldName);
        return soil.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<SoilData> getByUserId(@PathVariable String userId) {
        return soilService.getByUserId(userId);
    }

    @GetMapping("/regional")
    public List<SoilData> getByRegion(
            @RequestParam String state,
            @RequestParam(required = false) String district) {
        if (district != null && !district.isEmpty()) {
            return soilService.getByStateAndDistrict(state, district);
        } else {
            return soilService.getByState(state);
        }
    }
}
