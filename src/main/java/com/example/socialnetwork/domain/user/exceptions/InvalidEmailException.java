package com.example.socialnetwork.domain.user.exceptions;

public class InvalidEmailException extends RuntimeException{
    public static final String MESSAGE = "Invalid email: ";
    public InvalidEmailException(String email){
        super(MESSAGE + email);
    }
}
