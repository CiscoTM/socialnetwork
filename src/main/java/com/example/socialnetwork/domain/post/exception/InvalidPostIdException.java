package com.example.socialnetwork.domain.post.exception;

public class InvalidPostIdException extends RuntimeException{
    public InvalidPostIdException(String message){
        super(message);
    }
}
