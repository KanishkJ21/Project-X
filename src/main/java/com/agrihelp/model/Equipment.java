package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "equipment")
public class Equipment {

    @Id
    private String id;
    private String name;
    private String status;
    private Date lastServiceDate;

    public Equipment() {}
    public Equipment(String name, String status, Date lastServiceDate) {
        this.name = name;
        this.status = status;
        this.lastServiceDate = lastServiceDate;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(Date lastServiceDate) { this.lastServiceDate = lastServiceDate; }
}

