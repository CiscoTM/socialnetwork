package com.example.socialnetwork.delivery.interactions.follow.dtos;

import java.time.Instant;
import java.util.UUID;

public record FollowResponse(UUID id, UUID follower, UUID followed, Instant createdAt) {}
