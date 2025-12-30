package com.example.socialnetwork.delivery.interactions.comment.dtos;

import java.util.UUID;

public record CreateCommentRequest(UUID authorId, UUID postId, String content) {}
