package com.example.socialnetwork.infrastructure.persistence.post.jpa;

import com.example.socialnetwork.infrastructure.persistence.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPostRepository extends JpaRepository<PostEntity, UUID> {
}
