package com.example.socialnetwork.delivery.interactions.follow.controllers;

import com.example.socialnetwork.application.interactions.follow.FollowUserService;
import com.example.socialnetwork.delivery.interactions.follow.dtos.FollowRequest;
import com.example.socialnetwork.domain.user.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowUserService service;

    public FollowController(FollowUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> follow(@RequestBody FollowRequest request) {

        service.execute(
                UserId.of(request.followerId()),
                UserId.of(request.followedId())
        );

        return ResponseEntity.ok().build();
    }
}
