package com.agrihelp.controller;

import com.agrihelp.model.Alert;
import com.agrihelp.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/getAll")
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/type/{type}")
    public List<Alert> getAlertsByType(@PathVariable String type) {
        return alertService.getAlertsByType(type);
    }

    @PostMapping("/add")
    public Alert addAlert(@RequestBody Alert alert) {
        return alertService.addAlert(alert);
    }
}
