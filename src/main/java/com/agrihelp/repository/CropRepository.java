package com.agrihelp.repository;

import com.agrihelp.model.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends MongoRepository<Crop, String> {

    /**
     * Find a crop by its exact name.
     */
    Optional<Crop> findByName(String name);

    /**
     * Find crops suitable for a given soil pH range.
     */
    List<Crop> findByMinPHLessThanEqualAndMaxPHGreaterThanEqual(double ph1, double ph2);

    /**
     * Find crops suitable for a given rainfall range.
     */
    List<Crop> findByMinRainfallLessThanEqualAndMaxRainfallGreaterThanEqual(double rainfall1, double rainfall2);

    /**
     * Combined query: crops matching both soil pH and rainfall.
     */
    List<Crop> findByMinPHLessThanEqualAndMaxPHGreaterThanEqualAndMinRainfallLessThanEqualAndMaxRainfallGreaterThanEqual(
            double phMin, double phMax,
            double rainMin, double rainMax
    );
}
