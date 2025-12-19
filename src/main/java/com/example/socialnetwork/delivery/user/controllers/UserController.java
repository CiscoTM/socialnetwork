package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.delivery.user.dtos.UserRegistrationRequest;
import com.example.socialnetwork.delivery.user.dtos.UserResponse;
import com.example.socialnetwork.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRegistrationService registrationService;

    public UserController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = registrationService.register(
                request.id(),
                request.email(),
                request.displayName()
        );

        UserResponse response = UserResponse.fromDomain(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

