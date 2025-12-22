package com.example.socialnetwork.delivery.shared;

import com.example.socialnetwork.domain.user.exceptions.InvalidDisplayNameException;
import com.example.socialnetwork.domain.user.exceptions.InvalidEmailException;
import com.example.socialnetwork.domain.user.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiErrorHandler {

    @NotNull
    private ResponseEntity<ApiError> build(
            @NotNull HttpStatus status,
            @NotNull String message,
            @NotNull HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public @NotNull ResponseEntity<ApiError> handleUserAlreadyExists(
            @NotNull UserAlreadyExistsException ex,
            @NotNull HttpServletRequest request
    ) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler({InvalidEmailException.class, InvalidDisplayNameException.class})
    public @NotNull ResponseEntity<ApiError> handleValidation(
            @NotNull RuntimeException ex,
            @NotNull HttpServletRequest request
    ) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @NotNull ResponseEntity<ApiError> handleSpringValidation(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpServletRequest request
    ) {
        String message = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        return build(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(Exception.class)
    public @NotNull ResponseEntity<ApiError> handleGeneric(
            @NotNull Exception ex,
            @NotNull HttpServletRequest request
    ) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", request);
    }
}

