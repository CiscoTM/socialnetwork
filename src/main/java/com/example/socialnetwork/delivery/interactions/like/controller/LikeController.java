package com.example.socialnetwork.delivery.interactions.like.controller;

import com.example.socialnetwork.application.interactions.like.LikePostService;
import com.example.socialnetwork.delivery.interactions.like.LikeMapper;
import com.example.socialnetwork.delivery.interactions.like.dtos.LikeRequest;
import com.example.socialnetwork.delivery.interactions.like.dtos.LikeResponse;
import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikePostService service;

    public LikeController(LikePostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LikeResponse>like(@RequestBody LikeRequest request){
        Like like = service.execute(
                AuthorId.of(request.authorId()),
                PostId.of(request.postId())
        );
        return ResponseEntity.ok(LikeMapper.toResponse(like));
    }
}
