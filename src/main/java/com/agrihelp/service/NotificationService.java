package com.agrihelp.service;

import com.agrihelp.model.Alert;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    // Store alerts in memory (for demo; you can also store in MongoDB)
    private final List<Alert> alerts = new ArrayList<>();

    // Add a new alert
    public Alert createAlert(String type, String message) {
        Alert alert = new Alert(type, message, LocalDate.now());
        alerts.add(alert);
        return alert;
    }

    // Get all alerts
    public List<Alert> getAllAlerts() {
        return new ArrayList<>(alerts);
    }

    // Get alerts by type
    public List<Alert> getAlertsByType(String type) {
        List<Alert> filtered = new ArrayList<>();
        for (Alert alert : alerts) {
            if (alert.getType().equalsIgnoreCase(type)) {
                filtered.add(alert);
            }
        }
        return filtered;
    }

    // Example: Remove alerts older than a given date
    public void removeOldAlerts(LocalDate beforeDate) {
        alerts.removeIf(alert -> alert.getDate().isBefore(beforeDate));
    }
}
