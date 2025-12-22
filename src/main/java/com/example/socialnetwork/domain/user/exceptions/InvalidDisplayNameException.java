package com.example.socialnetwork.domain.user.exceptions;

public class InvalidDisplayNameException extends RuntimeException {
    public static final String MESSAGE = "Invalid display name: ";
    public InvalidDisplayNameException(String displayName) {
        super(MESSAGE + displayName);
    }
}

