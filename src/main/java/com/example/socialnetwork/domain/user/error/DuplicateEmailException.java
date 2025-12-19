package com.example.socialnetwork.domain.user.error;

public class DuplicateEmailException extends RuntimeException {
    public static final String MESSAGE = "Email already exists";

    public DuplicateEmailException(String email) {
        super(MESSAGE + ": " + email);
    }
}
