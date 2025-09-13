package com.agrihelp.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "soil_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoilData {

    @Id
    private String id;

    private String fieldName;    // Name of farmer's field
    private String region;       // e.g., state/district
    private String soilType;     // e.g., Loamy, Sandy, Clay
    private double ph;
    private double nitrogen;
    private double phosphorus;
    private double potassium;
    private LocalDate lastTested;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
