package com.example.socialnetwork.domain.post.exception;

public class InvalidPostContentException extends RuntimeException {
    public InvalidPostContentException(String message) {
        super(message);
    }
}
