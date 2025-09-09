package com.agrihelp.repository;

import com.agrihelp.model.Fertilizer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FertilizerRepository extends MongoRepository<Fertilizer, String> {

    /**
     * Find fertilizers by crop type.
     */
    List<Fertilizer> findByCropType(String cropType);

    /**
     * Find fertilizers that provide at least the required Nitrogen content.
     */
    List<Fertilizer> findByNitrogenContentGreaterThanEqual(double nitrogen);

    /**
     * Find fertilizers that provide at least the required Phosphorus content.
     */
    List<Fertilizer> findByPhosphorusContentGreaterThanEqual(double phosphorus);

    /**
     * Find fertilizers that provide at least the required Potassium content.
     */
    List<Fertilizer> findByPotassiumContentGreaterThanEqual(double potassium);

    /**
     * Custom query: Get fertilizers that match crop type + nutrient needs.
     */
    List<Fertilizer> findByCropTypeAndNitrogenContentGreaterThanEqualAndPhosphorusContentGreaterThanEqualAndPotassiumContentGreaterThanEqual(
            String cropType, double nitrogen, double phosphorus, double potassium
    );
}
