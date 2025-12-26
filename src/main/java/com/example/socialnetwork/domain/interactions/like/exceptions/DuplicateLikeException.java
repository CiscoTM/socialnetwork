package com.example.socialnetwork.domain.interactions.like.exceptions;

public class DuplicateLikeException extends RuntimeException{
    public DuplicateLikeException(String message) {
        super(message);
    }
}
