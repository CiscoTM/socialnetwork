package com.example.socialnetwork.delivery.interactions.comment.controllers;

import com.example.socialnetwork.application.interactions.comment.CreateCommentService;
import com.example.socialnetwork.delivery.interactions.comment.CommentMapper;
import com.example.socialnetwork.delivery.interactions.comment.dtos.CommentResponse;
import com.example.socialnetwork.delivery.interactions.comment.dtos.CreateCommentRequest;
import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CreateCommentService service;

    public CommentController(CreateCommentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CommentResponse>create(@RequestBody CreateCommentRequest request){
        Comment comment = service.execute(
                AuthorId.of(request.authorId()),
                PostId.of(request.postId()),
                request.content()
        );
        return ResponseEntity.ok(CommentMapper.toResponse(comment));
    }

}
