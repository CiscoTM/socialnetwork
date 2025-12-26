package com.example.socialnetwork.domain.interactions.comment.ports;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.CommentId;
import com.example.socialnetwork.domain.post.PostId;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void save(Comment comment);
    Optional<Comment>findById(CommentId id);
    List<Comment>findByPostId(PostId postId);
}
