package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidPostContentException;
import com.example.socialnetwork.domain.post.exception.NullPostContentException;

public record PostContent(String value) {
    public PostContent {
        if(value == null){
            throw new NullPostContentException("Post content cannot be empty");
        }
        if(value.isBlank()){
            throw new InvalidPostContentException("Post content cannot be empty");
        }
        value = value.trim();
    }
    public static PostContent of(String value){
        return new PostContent(value);
    }
}
