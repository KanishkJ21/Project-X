package com.agrihelp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String phoneNumber;
    private String aadhaarNumber;
    private String password;

    // New fields for SHC regional support
    private String state;
    private String district;

    // New field for tracking SHC skip option
    private boolean shcSkipped = false;

    // Constructors
    public User() {}

    public User(String name, String email, String phoneNumber, String aadhaarNumber,
                String password, String state, String district, boolean shcSkipped) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadhaarNumber = aadhaarNumber;
        this.password = password;
        this.state = state;
        this.district = district;
        this.shcSkipped = shcSkipped;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public boolean isShcSkipped() { return shcSkipped; }
    public void setShcSkipped(boolean shcSkipped) { this.shcSkipped = shcSkipped; }
}
