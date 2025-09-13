package com.agrihelp.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "crop_recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CropRecommendation {

    @Id
    private String id;

    private String soilType;        // e.g., Loamy, Clay
    private double ph;              // optional if SHC skipped
    private String region;          // e.g., Punjab, Maharashtra
    private String recommendedCrop; // e.g., Wheat, Rice
    private String reason;          // Why this crop is suitable
    private boolean basedOnSoilData; // true = accurate, false = general suggestion

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
