package com.example.socialnetwork.delivery.interactions.follow.dtos;

import java.util.UUID;

public record FollowRequest(UUID followerId, UUID followedId) {}
