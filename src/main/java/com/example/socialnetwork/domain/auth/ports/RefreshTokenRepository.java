package com.example.socialnetwork.domain.auth.ports;

import com.example.socialnetwork.domain.auth.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    void save(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void delete(UUID userId);
}
