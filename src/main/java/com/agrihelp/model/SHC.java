package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shc")
public class SHC {

    @Id
    private String id;

    private String userId; // Reference to User
    private double ph;
    private double nitrogen;
    private double phosphorus;
    private double potassium;

    public SHC() {}

    public SHC(String userId, double ph, double nitrogen, double phosphorus, double potassium) {
        this.userId = userId;
        this.ph = ph;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public double getPh() { return ph; }
    public void setPh(double ph) { this.ph = ph; }

    public double getNitrogen() { return nitrogen; }
    public void setNitrogen(double nitrogen) { this.nitrogen = nitrogen; }

    public double getPhosphorus() { return phosphorus; }
    public void setPhosphorus(double phosphorus) { this.phosphorus = phosphorus; }

    public double getPotassium() { return potassium; }
    public void setPotassium(double potassium) { this.potassium = potassium; }
}
