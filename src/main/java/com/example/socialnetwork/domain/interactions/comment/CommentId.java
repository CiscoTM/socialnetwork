package com.example.socialnetwork.domain.interactions.comment;

import java.util.UUID;

public record CommentId(UUID value) {
    public CommentId {
        if(value == null){
            throw new IllegalArgumentException("CommentId cannot be null");
        }
    }
    public static CommentId generate(){
        return new CommentId(UUID.randomUUID());
    }
    public static CommentId of(UUID value){
        return new CommentId(value);
    }
}
