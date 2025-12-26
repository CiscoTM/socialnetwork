package com.example.socialnetwork.domain.interactions.follow.exceptions;

public class SelfFollowNotAllowedException extends RuntimeException{
    public SelfFollowNotAllowedException(String message) {
        super(message);
    }
}
