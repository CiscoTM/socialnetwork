package com.example.socialnetwork.infrastructure.persistence.user.jpa;

import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity>findByEmail(String email);
    boolean existsByEmail(String email);
}
