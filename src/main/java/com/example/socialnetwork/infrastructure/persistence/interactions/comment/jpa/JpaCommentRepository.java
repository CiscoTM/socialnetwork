package com.example.socialnetwork.infrastructure.persistence.interactions.comment.jpa;

import com.example.socialnetwork.infrastructure.persistence.interactions.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, UUID> {
    List<CommentEntity> findByPostId(UUID postId);
}
