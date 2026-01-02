package com.example.socialnetwork.infrastructure.persistence.interactions.follow.jpa;

import com.example.socialnetwork.infrastructure.persistence.interactions.follow.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaFollowRepository extends JpaRepository<FollowEntity, UUID> {

    Optional<FollowEntity> findByFollowerAndFollowed(UUID follower, UUID followed);
}
