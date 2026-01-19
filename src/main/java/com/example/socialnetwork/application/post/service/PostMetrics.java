package com.example.socialnetwork.application.post.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PostMetrics {

    private final Counter postsCreatedCounter;

    public PostMetrics(MeterRegistry registry) {
        this.postsCreatedCounter = Counter.builder("socialnetwork.posts.created")
                .description("Número total de posts creados")
                .register(registry);
    }

    public void incrementPostsCreated() {
        postsCreatedCounter.increment();
    }
}
