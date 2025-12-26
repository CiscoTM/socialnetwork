package com.example.socialnetwork.domain.interactions.comment;


import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.UUID;


public class CommentTest {
    @Test
    void create_comment_successfully(){
        CommentId id = CommentId.generate();
        AuthorId authorId = AuthorId.of(UUID.randomUUID());
        PostId postId = PostId.generate();
        CommentContent content = CommentContent.of("Nice post!");
        Comment comment = Comment.create(id, authorId, postId, content);

        assertThat(comment.id()).isEqualTo(id);
        assertThat(comment.authorId()).isEqualTo(authorId);
        assertThat(comment.postId()).isEqualTo(postId);
        assertThat(comment.content()).isEqualTo(content);
        assertThat(comment.createdAt()).isNotNull();
    }
}
