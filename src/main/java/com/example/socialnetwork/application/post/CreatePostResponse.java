package com.example.socialnetwork.application.post;

import java.time.Instant;
import java.util.UUID;

public record CreatePostResponse(UUID PostId, UUID authorId, String content, Instant createdAt) {
}
