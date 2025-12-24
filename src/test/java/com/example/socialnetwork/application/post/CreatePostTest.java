package com.example.socialnetwork.application.post;

import com.example.socialnetwork.application.post.service.PostApplicationService;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreatePostTest {
    @Test
    void shouldCreatePostSuccessfully(){
        PostRepository repository = mock(PostRepository.class);
        PostApplicationService service = new PostApplicationService(repository);

        CreatePostCommand command = new CreatePostCommand(
                UUID.randomUUID(),
                "Hello world"
        );

        CreatePostResponse response = service.createPost(command);

        assertNotNull(response.postId());
        assertEquals(command.authorId(), response.authorId());
        assertEquals(command.content(), response.content());

        verify(repository, times(1)).save(any(Post.class));
    }
}
