package com.agrihelp.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * MarketPrice model represents daily crop price data
 * stored in MongoDB with cache timestamps.
 */
@Document(collection = "market_prices")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketPrice {

    @Id
    private String id;

    private String cropName;    // Commodity name (e.g., "Wheat", "Rice")
    private String location;    // State or district
    private String marketName;  // Specific mandi/market (e.g., "Delhi Azadpur Mandi")

    private double price;       // Price value
    private String unit;        // e.g., "quintal", "kg"

    private LocalDate priceDate;      // Date when this price is valid (from API)
    
    @CreatedDate
    private LocalDateTime createdAt;  // Timestamp when saved in DB

    @LastModifiedDate
    private LocalDateTime updatedAt;  // Timestamp when record was last modified

    private LocalDateTime lastUpdated; // Useful for cache validity checks (API fetch time)
}
