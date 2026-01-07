package com.example.socialnetwork.delivery.user.dtos;

import com.example.socialnetwork.domain.user.User;

import java.time.Instant;

public record UserResponse(
        String id,
        String email,
        String displayName,
        Instant createdAt
) {

    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.id().value().toString(),
                user.email().value(),
                user.displayName(),
                user.createdAt()
        );
    }
}
