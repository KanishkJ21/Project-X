package com.agrihelp.service;

import com.agrihelp.model.CommunityPost;
import com.agrihelp.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    public CommunityPost addPost(CommunityPost post) {
        return communityRepository.save(post);
    }

    public List<CommunityPost> getAllPosts() {
        return communityRepository.findAll();
    }
}
