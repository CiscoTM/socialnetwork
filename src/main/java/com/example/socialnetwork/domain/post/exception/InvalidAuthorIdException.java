package com.example.socialnetwork.domain.post.exception;

public class InvalidAuthorIdException extends RuntimeException{
    public InvalidAuthorIdException(String message) {
        super(message);
    }
}
