package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pests")
public class Pest {

    @Id
    private String id;
    private String cropType;
    private String soilType; // added for SHC linkage
    private String name;
    private String season;

    public Pest() {}
    public Pest(String cropType, String soilType, String name, String season) {
        this.cropType = cropType;
        this.soilType = soilType; // initialize
        this.name = name;
        this.season = season;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
}
