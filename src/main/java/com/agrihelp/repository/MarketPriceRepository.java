package com.agrihelp.repository;

import com.agrihelp.model.MarketPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketPriceRepository extends MongoRepository<MarketPrice, String> {
    List<MarketPrice> findByCommodity(String commodity);
}
