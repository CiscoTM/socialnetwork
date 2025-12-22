package com.example.socialnetwork.domain.user;

import java.util.regex.Pattern;

public record UserEmail(String value) {

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public UserEmail(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Invalid email");
        }
        String normalized = value.trim().toLowerCase();

        if (!EMAIL.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = normalized; // ✅ ahora sí funciona
    }

    public static UserEmail of(String value) {
        return new UserEmail(value);
    }
}

