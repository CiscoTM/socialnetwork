package com.example.socialnetwork.domain.interactions.comment;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;

import java.time.Instant;

public record Comment(
        CommentId id,
        AuthorId authorId,
        PostId postId,
        CommentContent content,
        Instant createdAt
        ) {
    public static Comment create(CommentId id, AuthorId authorId, PostId postId, CommentContent content){
        return new Comment(id, authorId, postId, content, Instant.now());
    }
}
