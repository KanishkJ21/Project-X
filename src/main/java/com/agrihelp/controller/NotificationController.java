package com.agrihelp.controller;

import com.agrihelp.model.Alert;
import com.agrihelp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public Alert createAlert(@RequestParam String type, @RequestParam String message) {
        return notificationService.createAlert(type, message);
    }

    @GetMapping("/all")
    public List<Alert> getAllAlerts() {
        return notificationService.getAllAlerts();
    }

    @GetMapping("/type/{type}")
    public List<Alert> getAlertsByType(@PathVariable String type) {
        return notificationService.getAlertsByType(type);
    }
}
