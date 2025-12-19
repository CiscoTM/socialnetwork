package com.example.socialnetwork.delivery.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequest (@NotBlank String id, @Email String email, @NotBlank String displayName){}
