package com.agrihelp.repository;

import com.agrihelp.model.Pest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PestRepository extends MongoRepository<Pest, String> {
    List<Pest> findByCropType(String cropType);
}
