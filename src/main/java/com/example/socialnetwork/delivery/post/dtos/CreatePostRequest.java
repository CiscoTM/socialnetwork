package com.example.socialnetwork.delivery.post.dtos;

import java.util.UUID;

public record CreatePostRequest(UUID authorId, String content) {}
