package com.example.socialnetwork.infrastructure.persistence.auth;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiredAt;
    @Column(nullable = false)
    private Instant createdAt;

    public RefreshTokenEntity() {}

    public RefreshTokenEntity(UUID id, UUID userId, String token, Instant expiredAt,Instant createdAt ) {
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }
}
