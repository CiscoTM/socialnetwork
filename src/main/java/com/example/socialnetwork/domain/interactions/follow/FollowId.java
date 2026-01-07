package com.example.socialnetwork.domain.interactions.follow;

import java.util.UUID;

public record FollowId(UUID value) {

    public FollowId {
        if (value == null) {
            throw new IllegalArgumentException("FollowId cannot be null");
        }
    }

    public static FollowId of(UUID value) {
        return new FollowId(value);
    }

    public static FollowId fromString(String value) {
        return new FollowId(UUID.fromString(value));
    }

    public static FollowId generate() {
        return new FollowId(UUID.randomUUID());
    }
}
