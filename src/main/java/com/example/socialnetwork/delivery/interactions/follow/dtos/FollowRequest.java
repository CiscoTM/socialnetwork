package com.example.socialnetwork.delivery.interactions.follow.dtos;

import java.util.UUID;

public record FollowRequest(UUID follower, UUID followed) {}
