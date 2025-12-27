package com.example.socialnetwork.application.interactions.like;


import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.interactions.like.exceptions.DuplicateLikeException;
import com.example.socialnetwork.domain.interactions.like.ports.LikeRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class LikePostServiceTest {
    private final LikeRepository repository = mock(LikeRepository.class);
    private final LikePostService service = new LikePostService(repository);

    @Test
    void creates_like_successfully(){
        AuthorId authorId = AuthorId.of(UUID.randomUUID());
        PostId postId = PostId.generate();

        Like result = service.execute(authorId, postId);

        assertThat(result.authorId().value()).isEqualTo(authorId.value());
        assertThat(result.postId().value()).isEqualTo(postId.value());
        verify(repository).save(any(Like.class));
    }
    @Test
    void duplicate_like_throws_exception(){
        AuthorId authorId = AuthorId.of(UUID.randomUUID());
        PostId postId = PostId.generate();

        when(repository.findByAuthorAndPost(authorId, postId))
                .thenReturn(Optional.of(mock(Like.class)));

        assertThatThrownBy(() -> service.execute(authorId, postId))
                .isInstanceOf(DuplicateLikeException.class);
    }
}
