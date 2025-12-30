package com.example.socialnetwork.delivery.interactions.like.dtos;

import java.util.UUID;

public record LikeRequest(UUID authorId, UUID postId) {}
