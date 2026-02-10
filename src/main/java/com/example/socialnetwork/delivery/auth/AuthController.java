package com.example.socialnetwork.delivery.auth;

import com.example.socialnetwork.application.auth.AuthenticateUserService;
import com.example.socialnetwork.application.auth.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticateUserService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticateUserService authService,
                          RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokens = authService.authenticate(request.username(), request.password());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        TokenResponse tokens = refreshTokenService.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok(tokens);
    }
}
