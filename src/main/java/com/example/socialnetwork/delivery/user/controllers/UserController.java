package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.delivery.shared.logging.ControllerLogging;
import com.example.socialnetwork.delivery.user.dtos.UserRegistrationRequest;
import com.example.socialnetwork.delivery.user.dtos.UserResponse;
import com.example.socialnetwork.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@Tag(name = "Users", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = ControllerLogging.getLogger(UserController.class);

    private final UserRegistrationService registrationService;

    public UserController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;

    }

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un usuario con email y displayName"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ya registrado")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = registrationService.register(
                request.id().toString(),
                request.email(),
                request.displayName()
        );

        UserResponse response = UserResponse.fromDomain(user);

        log.info("user.register.success userId={} email={}", user.id().value(), user.email().value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

