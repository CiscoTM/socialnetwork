package com.example.socialnetwork.infrastructure.persistence.interactions.like;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id","user_id"})
)
public class LikeEntity {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(name = "post_id", nullable = false)
    private UUID postId;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected LikeEntity(){}

    public LikeEntity(UUID id, UUID postId, UUID userId, Instant createdAt) {
        this.createdAt = createdAt;
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }
    // GETTER
    public Instant getCreatedAt() { return createdAt;   }
    public UUID getId() {return id; }
    public UUID getPostId() {   return postId;  }
    public UUID getUserId() {   return userId;  }
}
