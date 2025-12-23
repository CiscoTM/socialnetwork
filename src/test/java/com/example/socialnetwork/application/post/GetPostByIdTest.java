package com.example.socialnetwork.application.post;

import com.example.socialnetwork.application.post.exception.PostNotFoundException;
import com.example.socialnetwork.application.post.service.PostApplicationService;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetPostByIdTest {
    @Test
    void shouldReturnPostWhenExists(){
        PostRepository repository = mock(PostRepository.class);
        PostApplicationService service = new PostApplicationService(repository);

        PostId id = PostId.generate();
        Post post = Post.restore(
                id,
                AuthorId.of( UUID.randomUUID()),
                PostContent.of("hello"),
                Instant.now()
        );
        when(repository.findById(id)).thenReturn(Optional.of(post));
        GetPostByIdResponse response = service.getPostById(new GetPostByIdQuery(id.value()));

        assertEquals(id.value(), response.postId());
        assertEquals(post.content().value(), response.content());
    }
    @Test
    void shouldThrowExceptionWhenPostDoesNotExist(){
        PostRepository repository = mock(PostRepository.class);
        PostApplicationService service = new PostApplicationService(repository);

        UUID id = UUID.randomUUID();

        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class,
                () -> service.getPostById(new GetPostByIdQuery(id)));
    }
}
