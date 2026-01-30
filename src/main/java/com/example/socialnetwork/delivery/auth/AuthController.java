package com.example.socialnetwork.delivery.auth;

import com.example.socialnetwork.application.auth.AuthenticateUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ConditionalOnProperty(name = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticateUserService authService;

    public AuthController(AuthenticateUserService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = authService.authenticate(request.username(), request.password());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}