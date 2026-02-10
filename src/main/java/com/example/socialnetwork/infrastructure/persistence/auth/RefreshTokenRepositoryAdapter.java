package com.example.socialnetwork.infrastructure.persistence.auth;

import com.example.socialnetwork.domain.auth.RefreshToken;
import com.example.socialnetwork.domain.auth.ports.RefreshTokenRepository;
import com.example.socialnetwork.infrastructure.persistence.auth.jpa.RefreshTokenJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository jpa;

    public RefreshTokenRepositoryAdapter(RefreshTokenJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void save(RefreshToken token) {
        jpa.save(new RefreshTokenEntity(
                token.id(),
                token.userId(),
                token.token(),
                token.expiresAt(),
                token.createdAt()
                )
        );
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpa.findByToken(token)
                .map(e -> new RefreshToken(
                        e.getId(),
                        e.getUserId(),
                        e.getToken(),
                        e.getExpiredAt(),
                        e.getCreatedAt()
                ));
    }

    @Override
    public void delete(UUID userId) {
        jpa.deleteByUserId(userId);
    }
}
