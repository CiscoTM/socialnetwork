package com.example.socialnetwork.delivery.interactions.like.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LikeRequest(
        @NotNull(message = "authorId is required") UUID authorId,
        @NotNull(message = "postId is required") UUID postId
) {}