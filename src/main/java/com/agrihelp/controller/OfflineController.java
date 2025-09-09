package com.agrihelp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/offline")
public class OfflineController {

    private List<String> offlineData = new ArrayList<>();

    @PostMapping("/save")
    public String saveOfflineData(@RequestBody String data) {
        offlineData.add(data);
        return "Data saved offline successfully!";
    }

    @GetMapping("/getAll")
    public List<String> getOfflineData() {
        return offlineData;
    }

    @DeleteMapping("/clear")
    public String clearOfflineData() {
        offlineData.clear();
        return "Offline data cleared!";
    }
}
