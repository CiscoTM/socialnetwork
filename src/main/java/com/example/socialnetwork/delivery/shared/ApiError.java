package com.example.socialnetwork.delivery.shared;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record ApiError(
        @NotNull Instant timestamp,
        int status,
        @NotNull String error,
        @NotNull String message,
        @NotNull String path
) {}


