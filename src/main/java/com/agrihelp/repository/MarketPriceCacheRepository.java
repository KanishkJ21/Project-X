package com.agrihelp.repository;

import com.agrihelp.model.MarketPriceCache;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketPriceCacheRepository extends MongoRepository<MarketPriceCache, String> {

    // Most recent cache for commodity + state (for state-level fallback)
    Optional<MarketPriceCache> findTopByCommodityIgnoreCaseAndStateIgnoreCaseOrderByLastUpdatedDesc(
            String commodity, String state);

    // Most recent cache for commodity + state + market (for mandi-level data)
    Optional<MarketPriceCache> findTopByCommodityIgnoreCaseAndStateIgnoreCaseAndMarketIgnoreCaseOrderByLastUpdatedDesc(
            String commodity, String state, String market);
}
