package com.example.socialnetwork.delivery.post.controllers;

import com.example.socialnetwork.application.post.*;
import com.example.socialnetwork.application.post.service.PostApplicationService;
import com.example.socialnetwork.delivery.post.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostRestController {
    private final PostApplicationService service;

    public PostRestController(PostApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostRestResponse create(@RequestBody CreatePostRequest request){
        CreatePostResponse response = service.createPost(new CreatePostCommand(request.authorId(), request.content()));
        return new CreatePostRestResponse(
                response.postId(),
                response.authorId(),
                response.content(),
                response.createdAt()
        );
    }
    @GetMapping("/{id}")
    public GetPostRestResponse getById(@PathVariable("id") String id){
        GetPostByIdResponse response = service.getPostById(new GetPostByIdQuery(UUID.fromString(id)));
        return new GetPostRestResponse(
              response.postId(),
              response.authorId(),
              response.content(),
              response.createdAt()
        );
    }
}
