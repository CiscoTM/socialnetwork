package com.example.socialnetwork.domain.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.user.UserId;

import java.time.Instant;

public record Follow(
        FollowId id,
        UserId followerId,
        UserId followedId,
        Instant createdAt
) {

    public static Follow create(FollowId id, UserId followerId, UserId followedId) {

        if (followerId.equals(followedId)) {
            throw new SelfFollowNotAllowedException("A user cannot follow themselves");
        }

        return new Follow(id, followerId, followedId, Instant.now());
    }
}
