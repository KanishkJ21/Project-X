package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crops")
public class Crop {

    @Id
    private String id;

    private String name;
    private double minPH;
    private double maxPH;
    private double minRainfall;
    private double maxRainfall;
    private String sowingTime;

    // Optional fields to link to region (for SHC-based recommendations)
    private String state;
    private String district;

    // Constructors
    public Crop() {}

    public Crop(String name, double minPH, double maxPH, double minRainfall, double maxRainfall, String sowingTime, String state, String district) {
        this.name = name;
        this.minPH = minPH;
        this.maxPH = maxPH;
        this.minRainfall = minRainfall;
        this.maxRainfall = maxRainfall;
        this.sowingTime = sowingTime;
        this.state = state;
        this.district = district;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMinPH() { return minPH; }
    public void setMinPH(double minPH) { this.minPH = minPH; }

    public double getMaxPH() { return maxPH; }
    public void setMaxPH(double maxPH) { this.maxPH = maxPH; }

    public double getMinRainfall() { return minRainfall; }
    public void setMinRainfall(double minRainfall) { this.minRainfall = minRainfall; }

    public double getMaxRainfall() { return maxRainfall; }
    public void setMaxRainfall(double maxRainfall) { this.maxRainfall = maxRainfall; }

    public String getSowingTime() { return sowingTime; }
    public void setSowingTime(String sowingTime) { this.sowingTime = sowingTime; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
}
