package com.example.socialnetwork.domain.interactions.like;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;

import java.time.Instant;

public record Like(
        LikeId id,
        AuthorId authorId,
        PostId postId,
        Instant createdAt
) {
    public static Like create(LikeId id, AuthorId authorId, PostId postId){
        return new Like(id, authorId, postId, Instant.now());
    }
}
