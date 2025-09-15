package com.agrihelp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "market_price_cache")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketPriceCache {

    @Id
    private String id;

    private String commodity;  // e.g., "Wheat", "Rice"
    private String state;      // e.g., "Uttar Pradesh"
    private String market;     // Specific mandi/market (e.g., "Azadpur Mandi")

    private double price;
    private String unit;       // e.g., "quintal", "kg"
    private LocalDate priceDate;

    @LastModifiedDate
    private LocalDateTime lastUpdated; // for cache validity
}
