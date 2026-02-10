package com.example.socialnetwork.domain.auth;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(
        UUID id,
        UUID userId,
        String token,
        Instant expiresAt,
        Instant createdAt
) {
    public static RefreshToken create(UUID userId, String token, Instant expiresAt) {
        return new RefreshToken(
                UUID.randomUUID(),
                userId,
                token,
                expiresAt,
                Instant.now()
        );
    }
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
