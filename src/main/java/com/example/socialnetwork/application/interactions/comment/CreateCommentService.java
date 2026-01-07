package com.example.socialnetwork.application.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.CommentContent;
import com.example.socialnetwork.domain.interactions.comment.CommentId;
import com.example.socialnetwork.domain.interactions.comment.ports.CommentRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.springframework.stereotype.Service;

@Service
public class CreateCommentService {
    private final CommentRepository commentRepository;

    public CreateCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public Comment execute(AuthorId authorId, PostId postId, String content){
        Comment comment = Comment.create(CommentId.generate(), authorId, postId, CommentContent.of(content));
        commentRepository.save(comment);
        return comment;
    }
}
