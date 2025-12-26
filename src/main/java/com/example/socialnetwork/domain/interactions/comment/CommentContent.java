package com.example.socialnetwork.domain.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.exceptions.InvalidCommentContentException;

public record CommentContent(String value) {
    public CommentContent {
        if(value == null || value.isBlank()){
            throw new InvalidCommentContentException("Comment content cannot be empty");
        }
        if(value.length() > 500){
            throw new InvalidCommentContentException("Comment content exceeds 500 characters");
        }
    }
    public static CommentContent of(String value){
        return new CommentContent(value);
    }
}
