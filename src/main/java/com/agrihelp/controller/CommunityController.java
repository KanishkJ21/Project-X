package com.agrihelp.controller;

import com.agrihelp.model.CommunityPost;
import com.agrihelp.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/getAll")
    public List<CommunityPost> getAllPosts() {
        return communityService.getAllPosts();
    }

    @PostMapping("/post")
    public CommunityPost addPost(@RequestBody CommunityPost post) {
        return communityService.addPost(post);
    }
}
