package com.example.socialnetwork.delivery.post.dtos;

import java.time.Instant;
import java.util.UUID;

public record CreatePostRestResponse(UUID postId, UUID authorId, String content, Instant createdAt) {}
