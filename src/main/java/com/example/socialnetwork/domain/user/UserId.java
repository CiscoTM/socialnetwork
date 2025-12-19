package com.example.socialnetwork.domain.user;

public record UserId (String value){
    public UserId {
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException("UserId cannot be blank");        }
    }
    public static UserId of(String value){return new UserId(value);}
}
