package com.example.socialnetwork.delivery.user.dtos;

import java.time.Instant;

public record UserResponse(String id, String email, String displayName, Instant createAt) {}
