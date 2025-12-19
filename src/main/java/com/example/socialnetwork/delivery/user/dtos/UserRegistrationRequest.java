package com.example.socialnetwork.delivery.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank(message = "Id is required")
        String id,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Display name is required")
        @Size(min = 1, max = 50, message = "Display name must be between 1 and 50 characters")
        String displayName
) {
}

