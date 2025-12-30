package com.example.socialnetwork.delivery.interactions.like.dtos;

import java.time.Instant;
import java.util.UUID;

public record LikeResponse(UUID id, UUID authorId, UUID postId, Instant createdAt) {}
