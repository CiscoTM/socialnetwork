package com.example.socialnetwork.infrastructure.persistence.post.jpa;

import com.example.socialnetwork.infrastructure.persistence.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaPostRepository extends JpaRepository<PostEntity, UUID> {
}

