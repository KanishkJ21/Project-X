package com.agrihelp.repository;

import com.agrihelp.model.SHC;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SHCRepository extends MongoRepository<SHC, String> {

    // Fetch SHC record linked to a specific user
    Optional<SHC> findByUserId(String userId);

    // Fetch all SHCs in a specific state
    List<SHC> findByState(String state);

    // Fetch all SHCs in a specific state and district
    List<SHC> findByStateAndDistrict(String state, String district);
}
