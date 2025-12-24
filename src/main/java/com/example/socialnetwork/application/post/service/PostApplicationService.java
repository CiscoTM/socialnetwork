package com.example.socialnetwork.application.post.service;

import com.example.socialnetwork.application.post.*;
import com.example.socialnetwork.application.post.exception.PostNotFoundException;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostApplicationService {
    private final PostRepository repository;

    public PostApplicationService(PostRepository repository) {
        this.repository = repository;
    }

    public CreatePostResponse createPost(CreatePostCommand command){
        Post post = PostMapper.toDomain(command);
        repository.save(post);
        return PostMapper.toCreateResponse(post);
    }
    public GetPostByIdResponse getPostById(GetPostByIdQuery query){
        Post post = repository.findById(PostId.of(query.postId()))
                .orElseThrow(() -> new PostNotFoundException("Post not found: " + query.postId()));
        return PostMapper.toGetResponse(post);
    }
}