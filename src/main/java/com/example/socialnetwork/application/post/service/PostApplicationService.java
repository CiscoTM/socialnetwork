package com.example.socialnetwork.application.post.service;

import com.example.socialnetwork.application.post.*;
import com.example.socialnetwork.application.post.exception.PostNotFoundException;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class PostApplicationService {

    private final PostRepository repository;
    private final PostMetrics postMetrics;
    private final Timer postCreationTimer;

    public PostApplicationService(PostRepository repository,
                                  PostMetrics postMetrics,
                                  MeterRegistry meterRegistry) {

        this.repository = repository;
        this.postMetrics = postMetrics;

        // ✔️ Solución correcta: delegar en meterRegistry.timer(...)
        //    Esto funciona con mocks y con el registry real.
        this.postCreationTimer = meterRegistry.timer("socialnetwork.posts.creation.time");
    }

    public CreatePostResponse createPost(CreatePostCommand command) {
        return postCreationTimer.record(() -> {
            Post post = PostMapper.toDomain(command);
            repository.save(post);
            postMetrics.incrementPostsCreated();
            return PostMapper.toCreateResponse(post);
        });
    }

    public GetPostByIdResponse getPostById(GetPostByIdQuery query) {
        Post post = repository.findById(PostId.of(query.postId()))
                .orElseThrow(() -> new PostNotFoundException("Post not found: " + query.postId()));

        return PostMapper.toGetResponse(post);
    }
}
