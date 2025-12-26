package com.example.socialnetwork.domain.interactions.comment.exceptions;

public class InvalidCommentContentException extends RuntimeException{
    public InvalidCommentContentException(String message){
        super(message);
    }
}
