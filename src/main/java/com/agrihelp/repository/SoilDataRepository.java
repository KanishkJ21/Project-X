package com.agrihelp.repository;

import com.agrihelp.model.SoilData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoilDataRepository extends MongoRepository<SoilData, String> {
    Optional<SoilData> findByFieldNameIgnoreCase(String fieldName);
    List<SoilData> findByRegionIgnoreCase(String region);
}
