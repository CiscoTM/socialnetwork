package com.example.socialnetwork.delivery.logging;

import com.example.socialnetwork.application.auth.AuthenticateUserService;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Test
    @DisplayName("Debe permitir hacer follow con logging estructurado activo")
    void shouldFollowWithStructuredLogging() throws Exception {

        User follower = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("follower@example.com"),
                "Follower"
        );
        userRepository.save(follower);

        User followed = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("followed@example.com"),
                "Followed"
        );
        userRepository.save(followed);

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

    @Test
    @DisplayName("Debe permitir hacer like con logging estructurado activo")
    void shouldLikeWithStructuredLogging() throws Exception {

        User author = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("author@example.com"),
                "Author"
        );
        userRepository.save(author);

        PostContent content = PostContent.of("Contenido de prueba");

        Post post = Post.create(
                AuthorId.of(author.id().value()),
                content
        );
        postRepository.save(post);

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
