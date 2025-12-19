package com.example.socialnetwork.domain.user;

import java.time.Instant;

public record User (UserId id, UserEmail email, String displayName, Instant createdAt){
    public User {
        if(id == null) throw new IllegalArgumentException("UserId cannot be null");
        if(email == null || email.value().isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        if(displayName == null || displayName.isBlank() || displayName.length() < 3)
            throw new IllegalArgumentException("DisplayName must have at least 3 characters");
    }
    public static User restore(UserId id, UserEmail email, String displayName, Instant createdAt){
        return new User(id, email, displayName, createdAt);
    }
    public static User create(UserId id, UserEmail email, String displayName){
        return new User(id, email, displayName, Instant.now());
    }
}
