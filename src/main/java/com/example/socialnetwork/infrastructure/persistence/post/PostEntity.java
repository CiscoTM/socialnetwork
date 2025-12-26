package com.example.socialnetwork.infrastructure.persistence.post;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(columnDefinition = "uuid",nullable = false, name = "author_id")
    private UUID authorId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    protected PostEntity(){}

    public PostEntity(UUID id, UUID authorId, String content, Instant createdAt) {
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.id = id;
    }

    public UUID getId() { return id; }
    public UUID getAuthorId() { return authorId; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }


    public void setId(UUID id) { this.id = id; }
    public void setAuthorId(UUID authorId) { this.authorId = authorId; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

}
