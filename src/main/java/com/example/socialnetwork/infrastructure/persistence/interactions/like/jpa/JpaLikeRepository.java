package com.example.socialnetwork.infrastructure.persistence.interactions.like.jpa;

import com.example.socialnetwork.infrastructure.persistence.interactions.like.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, UUID> {
    Optional<LikeEntity> findByUserIdAndPostId(UUID userId, UUID postId);
}
