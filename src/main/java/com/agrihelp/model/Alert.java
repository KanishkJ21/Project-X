package com.agrihelp.model;

import java.time.LocalDate;

public class Alert {

    private String type;
    private String message;
    private LocalDate date;

    // Constructor
    public Alert(String type, String message, LocalDate date) {
        this.type = type;
        this.message = message;
        this.date = date;
    }

    // Default constructor
    public Alert() {
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
