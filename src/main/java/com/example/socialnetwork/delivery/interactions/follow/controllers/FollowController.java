package com.example.socialnetwork.delivery.interactions.follow.controllers;

import com.example.socialnetwork.application.interactions.follow.FollowUserService;
import com.example.socialnetwork.delivery.interactions.follow.FollowMapper;
import com.example.socialnetwork.delivery.interactions.follow.dtos.FollowRequest;
import com.example.socialnetwork.delivery.interactions.follow.dtos.FollowResponse;
import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.post.AuthorId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowUserService service;

    public FollowController(FollowUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FollowResponse>follow(@RequestBody FollowRequest request){
        Follow follow = service.execute(
                AuthorId.of(request.follower()),
                AuthorId.of(request.followed())
                );
        return ResponseEntity.ok(FollowMapper.toResponse(follow));
    }

}
