package com.example.socialnetwork.delivery.interactions.comment.dtos;

import java.rmi.server.UID;
import java.time.Instant;
import java.util.UUID;

public record CommentResponse(UUID id, UUID authorId, UUID postId, String content, Instant createdAt) {}
