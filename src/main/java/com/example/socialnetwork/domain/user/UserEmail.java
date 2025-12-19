package com.example.socialnetwork.domain.user;

import java.util.regex.Pattern;

public record UserEmail (String value){
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public UserEmail {
        if(value == null ||!EMAIL.matcher(value).matches()){
            throw new IllegalArgumentException("Invalid email");
        }
        value = value.toLowerCase();
    }    public static UserEmail of(String value) { return new UserEmail(value); }
}
