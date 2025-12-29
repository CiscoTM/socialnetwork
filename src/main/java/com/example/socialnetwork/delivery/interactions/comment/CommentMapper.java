package com.example.socialnetwork.delivery.interactions.comment;

import com.example.socialnetwork.delivery.interactions.comment.dtos.CommentResponse;
import com.example.socialnetwork.domain.interactions.comment.Comment;

import java.time.Instant;

public class CommentMapper {
    public static CommentResponse toResponse(Comment domain){
        return new CommentResponse(
                domain.id().value(),
                domain.authorId().value(),
                domain.postId().value(),
                domain.content().value(),
                domain.createdAt()
        );
    }
}
