package com.example.socialnetwork.infrastructure.persistence.auth.jpa;

import com.example.socialnetwork.infrastructure.persistence.auth.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUserId(UUID userId);
}
