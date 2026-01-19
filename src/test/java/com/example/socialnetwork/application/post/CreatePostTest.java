package com.example.socialnetwork.application.post;

import com.example.socialnetwork.application.post.service.PostApplicationService;
import com.example.socialnetwork.application.post.service.PostMetrics;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreatePostTest {

    @Test
    void shouldCreatePostSuccessfully() {

        // Mocks
        PostRepository repository = mock(PostRepository.class);
        PostMetrics postMetrics = mock(PostMetrics.class);

        // Registry real (NO mock)
        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        // Servicio bajo test
        PostApplicationService service =
                new PostApplicationService(repository, postMetrics, meterRegistry);

        // Comando de entrada
        CreatePostCommand command = new CreatePostCommand(
                UUID.randomUUID(),
                "Hello world"
        );

        // Ejecución
        CreatePostResponse response = service.createPost(command);

        // Validaciones
        assertNotNull(response.PostId());
        assertEquals(command.authorId(), response.authorId());
        assertEquals(command.content(), response.content());

        // Verificación de persistencia
        verify(repository, times(1)).save(any(Post.class));

        // Verificación de métrica
        verify(postMetrics, times(1)).incrementPostsCreated();
    }

}
