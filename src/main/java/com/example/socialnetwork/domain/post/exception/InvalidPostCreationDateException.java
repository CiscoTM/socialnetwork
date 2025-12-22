package com.example.socialnetwork.domain.post.exception;

public class InvalidPostCreationDateException extends RuntimeException {
    public InvalidPostCreationDateException(String message) {
        super(message);
    }
}
