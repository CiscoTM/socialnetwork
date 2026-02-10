package com.example.socialnetwork.application.auth;

import com.example.socialnetwork.delivery.auth.TokenResponse;
import com.example.socialnetwork.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public RefreshTokenService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse refreshAccessToken(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken) ||
                !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtTokenProvider.getUsername(refreshToken);

        String newAccessToken = jwtTokenProvider.generateAccessToken(email);

        return new TokenResponse(newAccessToken, refreshToken);
    }
}
