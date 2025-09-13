package com.agrihelp.repository;

import com.agrihelp.model.MarketPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketPriceRepository extends MongoRepository<MarketPrice, String> {

    // Find all prices for a crop (case-insensitive)
    List<MarketPrice> findByCropNameIgnoreCase(String cropName);

    // Find all prices for a location (case-insensitive)
    List<MarketPrice> findByLocationIgnoreCase(String location);

    // Find specific crop & location combination
    List<MarketPrice> findByCropNameIgnoreCaseAndLocationIgnoreCase(String cropName, String location);

    // Optional: Get the latest entry for a crop & location (sorted by lastUpdated descending)
    Optional<MarketPrice> findTopByCropNameIgnoreCaseAndLocationIgnoreCaseOrderByLastUpdatedDesc(
            String cropName,
            String location
    );
}
