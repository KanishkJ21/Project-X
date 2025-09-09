package com.agrihelp.repository;

import com.agrihelp.model.CommunityPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends MongoRepository<CommunityPost, String> {}
