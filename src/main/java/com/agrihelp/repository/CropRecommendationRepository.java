package com.agrihelp.repository;

import com.agrihelp.model.CropRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRecommendationRepository extends MongoRepository<CropRecommendation, String> {
    List<CropRecommendation> findBySoilTypeIgnoreCase(String soilType);
    List<CropRecommendation> findByRegionIgnoreCase(String region);
    List<CropRecommendation> findBySoilTypeIgnoreCaseAndPhBetween(String soilType, double minPh, double maxPh);
}
