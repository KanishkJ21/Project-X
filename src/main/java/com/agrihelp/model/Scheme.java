package com.agrihelp.model;

public class Scheme {

    private String name;
    private String description;
    private double amount;

    // Default constructor
    public Scheme() {}

    // Parameterized constructor
    public Scheme(String name, String description, double amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
