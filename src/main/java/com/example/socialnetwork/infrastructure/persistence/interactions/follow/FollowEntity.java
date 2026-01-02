package com.example.socialnetwork.infrastructure.persistence.interactions.follow;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
)
public class FollowEntity {
    @Id
    @Column(nullable = false)
    private UUID id;
    @Column(name = "follower_id", nullable = false)
    private UUID follower;
    @Column(name = "followed_id", nullable = false)
    private UUID followed;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected FollowEntity(){}

    public FollowEntity(UUID id, UUID follower, UUID followed, Instant createdAt) {
        this.id = id;
        this.follower = follower;
        this.followed = followed;
        this.createdAt = createdAt;
    }

    //GETTERS
    public Instant getCreatedAt() { return createdAt;  }
    public UUID getFollowed() { return followed;    }
    public UUID getFollower() { return follower;    }
    public UUID getId() {   return id;  }
}
