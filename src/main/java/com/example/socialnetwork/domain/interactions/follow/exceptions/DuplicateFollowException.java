package com.example.socialnetwork.domain.interactions.follow.exceptions;

public class DuplicateFollowException extends RuntimeException {
    public DuplicateFollowException(String message) {
        super(message);
    }
}
