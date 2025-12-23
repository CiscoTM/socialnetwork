package com.example.socialnetwork.application.post;

import java.util.UUID;

public record CreatePostCommand(UUID authorId, String content) {}
