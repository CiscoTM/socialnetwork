package com.example.socialnetwork.domain.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.post.AuthorId;

import java.time.Instant;

public record Follow(
        FollowId id,
        AuthorId followerId,
        AuthorId followedId,
        Instant createdAt
) {
    public static Follow create(FollowId id, AuthorId followerId, AuthorId followedId){
        if(followerId.equals(followedId)){
            throw new SelfFollowNotAllowedException("A user cannot follow themselves");
        }
        return new Follow(id, followerId, followedId, Instant.now());
    }
}
