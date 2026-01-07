package com.example.socialnetwork.domain.interactions.like;

import java.util.UUID;

public record LikeId(UUID value) {

    public LikeId {
        if (value == null) {
            throw new IllegalArgumentException("LikeId cannot be null");
        }
    }

    public static LikeId of(UUID value) {
        return new LikeId(value);
    }

    public static LikeId fromString(String value) {
        return new LikeId(UUID.fromString(value));
    }

    public static LikeId generate() {
        return new LikeId(UUID.randomUUID());
    }
}
