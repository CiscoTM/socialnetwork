package com.example.socialnetwork.delivery.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRegistrationRequest(
        @NotNull UUID id,
        @Email String email,
        @NotBlank String displayName
) {}


