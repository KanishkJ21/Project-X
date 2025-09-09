package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "soil")
public class SoilData {

    @Id
    private String id;

    private String userId;   // Link soil data to a specific user/farmer
    private String fieldName;
    private String state;    
    private String district;

    private Double ph;
    private Double nitrogen;
    private Double phosphorus;
    private Double potassium;
    private Double moisture;

    private Instant createdAt;
    private Instant updatedAt;

    // --- Getters ---
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getFieldName() { return fieldName; }
    public String getState() { return state; }
    public String getDistrict() { return district; }
    public Double getPh() { return ph; }
    public Double getNitrogen() { return nitrogen; }
    public Double getPhosphorus() { return phosphorus; }
    public Double getPotassium() { return potassium; }
    public Double getMoisture() { return moisture; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // --- Setters ---
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public void setState(String state) { this.state = state; }
    public void setDistrict(String district) { this.district = district; }
    public void setPh(Double ph) { this.ph = ph; }
    public void setNitrogen(Double nitrogen) { this.nitrogen = nitrogen; }
    public void setPhosphorus(Double phosphorus) { this.phosphorus = phosphorus; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }
    public void setMoisture(Double moisture) { this.moisture = moisture; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
