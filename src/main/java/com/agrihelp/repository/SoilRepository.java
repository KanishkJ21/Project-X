package com.agrihelp.repository;

import com.agrihelp.model.SoilData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoilRepository extends MongoRepository<SoilData, String> {

    /**
     * Find soil data by field name (unique per user/field).
     */
    Optional<SoilData> findByFieldName(String fieldName);

    /**
     * Find all soil records for a specific user.
     */
    List<SoilData> findByUserId(String userId);

    /**
     * Find all soil records by state and district
     * (useful for fallback regional analysis).
     */
    List<SoilData> findByStateAndDistrict(String state, String district);

    List<SoilData> findByState(String state);
}
