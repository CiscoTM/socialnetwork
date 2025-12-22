package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidPostIdException;

import java.util.UUID;

public record PostId(UUID value) {
    public PostId {
        if(value == null){
            throw new InvalidPostIdException("PostId cannot be null");
        }
    }
    public static PostId generate(){
        return new PostId(UUID.randomUUID());
    }
    public static PostId of(UUID value){
        return new PostId(value);
    }
}
