package com.example.socialnetwork.domain.user.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public static final String MESSAGE = "A user with email '%s' already exists";
    public UserAlreadyExistsException(String email){
        super(String.format(MESSAGE, email));
    }
}
