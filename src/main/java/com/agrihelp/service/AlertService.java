package com.agrihelp.service;

import com.agrihelp.model.Alert;
import com.agrihelp.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public Alert addAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getAlertsByType(String type) {
        return alertRepository.findByType(type);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
