package com.example.socialnetwork.application.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.ports.CommentRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class CreateCommentServiceTest {
    private final CommentRepository repository = mock(CommentRepository.class);
    private final CreateCommentService service = new CreateCommentService(repository);

    @Test
    void creates_comment_successfully(){
        PostId postId = PostId.generate();
        AuthorId authorId = AuthorId.of(UUID.randomUUID());

        Comment result = service.execute(authorId, postId, "Hello");

        assertThat(result.authorId()).isEqualTo(authorId);
        assertThat(result.postId().value()).isEqualTo(postId.value());
        assertThat(result.content().value()).isEqualTo("Hello");
        assertThat(result.createdAt()).isNotNull();

        verify(repository).save(any(Comment.class));
    }
}
