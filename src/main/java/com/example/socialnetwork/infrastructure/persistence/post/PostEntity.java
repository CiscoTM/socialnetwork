package com.example.socialnetwork.infrastructure.persistence.post;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    private UUID id;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected PostEntity() {}

    public PostEntity(UUID id, UUID authorId, String content, Instant createdAt) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getAuthorId() { return authorId; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }
}
