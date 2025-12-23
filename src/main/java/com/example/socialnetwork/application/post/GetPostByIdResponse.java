package com.example.socialnetwork.application.post;

import java.time.Instant;
import java.util.UUID;

public record GetPostByIdResponse(UUID postId, UUID authorId, String content, Instant createdAt) {
}
