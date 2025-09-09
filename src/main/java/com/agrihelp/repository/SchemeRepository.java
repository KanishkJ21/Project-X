package com.agrihelp.repository;

import com.agrihelp.model.Scheme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeRepository extends MongoRepository<Scheme, String> {}
