package com.example.socialnetwork.delivery.logging;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import support.RestIntegrationTestBase;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoggingIntegrationTest extends RestIntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // ------------------------------------------------------------
    // 1. Test: registrar usuario (no necesita datos previos)
    // ------------------------------------------------------------
    @Test
    @DisplayName("Debe permitir registrar un usuario con logging estructurado activo")
    void shouldRegisterUserWithStructuredLogging() throws Exception {

        String body = """
                {
                  "id": "%s",
                  "email": "user21@example.com",
                  "displayName": "User 21"
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(
                        post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-Correlation-Id", UUID.randomUUID().toString())
                                .content(body)
                )
                .andExpect(status().isCreated());
    }

    // ------------------------------------------------------------
    // 2. Test: follow (requiere 2 usuarios válidos)
    // ------------------------------------------------------------
    @Test
    @DisplayName("Debe permitir hacer follow con logging estructurado activo")
    void shouldFollowWithStructuredLogging() throws Exception {

        // Crear usuario follower
        User follower = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("follower@example.com"),
                "Follower"
        );
        userRepository.save(follower);

        // Crear usuario followed
        User followed = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("followed@example.com"),
                "Followed"
        );
        userRepository.save(followed);

        // Llamada al endpoint
        String body = """
                {
                  "follower": "%s",
                  "followed": "%s"
                }
                """.formatted(
                follower.id().value(),
                followed.id().value()
        );

        mockMvc.perform(
                        post("/api/follows")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-Correlation-Id", UUID.randomUUID().toString())
                                .content(body)
                )
                .andExpect(status().isOk());
    }

    // ------------------------------------------------------------
    // 3. Test: like (requiere usuario + post válido)
    // ------------------------------------------------------------
    @Test
    @DisplayName("Debe permitir hacer like con logging estructurado activo")
    void shouldLikeWithStructuredLogging() throws Exception {

        // Crear usuario autor
        User author = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("author@example.com"),
                "Author"
        );
        userRepository.save(author);

        // Crear contenido del post (Value Object)
        PostContent content = PostContent.of("Contenido de prueba");

        // Crear post válido según tu dominio
        Post post = Post.create(
                AuthorId.of(author.id().value()),
                content
        );
        postRepository.save(post);

        // Llamada al endpoint
        String body = """
                {
                  "authorId": "%s",
                  "postId": "%s"
                }
                """.formatted(
                author.id().value(),
                post.id().value()
        );

        mockMvc.perform(
                        post("/api/likes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("X-Correlation-Id", UUID.randomUUID().toString())
                                .content(body)
                )
                .andExpect(status().isOk());
    }
}
