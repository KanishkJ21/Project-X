package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fertilizers")
public class Fertilizer {

    @Id
    private String id;

    private String name;
    private String cropType;
    private String soilType; // <- add this
    private double recommendedN;
    private double recommendedP;
    private double recommendedK;

    public Fertilizer() {}

    public Fertilizer(String name, String cropType, String soilType, double recommendedN, double recommendedP, double recommendedK) {
        this.name = name;
        this.cropType = cropType;
        this.soilType = soilType;
        this.recommendedN = recommendedN;
        this.recommendedP = recommendedP;
        this.recommendedK = recommendedK;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

    public String getSoilType() { return soilType; } // <- add getter
    public void setSoilType(String soilType) { this.soilType = soilType; } // <- add setter

    public double getRecommendedN() { return recommendedN; }
    public void setRecommendedN(double recommendedN) { this.recommendedN = recommendedN; }

    public double getRecommendedP() { return recommendedP; }
    public void setRecommendedP(double recommendedP) { this.recommendedP = recommendedP; }

    public double getRecommendedK() { return recommendedK; }
    public void setRecommendedK(double recommendedK) { this.recommendedK = recommendedK; }
}
