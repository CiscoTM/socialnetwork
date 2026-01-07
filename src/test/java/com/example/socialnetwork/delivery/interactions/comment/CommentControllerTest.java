package com.example.socialnetwork.delivery.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.CommentContent;
import com.example.socialnetwork.domain.interactions.comment.CommentId;
import com.example.socialnetwork.domain.interactions.comment.ports.CommentRepository;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import support.RestIntegrationTestBase;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest extends RestIntegrationTestBase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    private User author;
    private Post post;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Crear usuario real usando tu dominio EXACTO
        author = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("test@example.com"),
                "Test User"
        );
        userRepository.save(author);

        // Crear post real usando Post.create(AuthorId, PostContent)
        post = Post.create(
                AuthorId.of(UUID.fromString(author.id().value().toString())),
                PostContent.of("Post de prueba")
        );
        postRepository.save(post);
    }

    @Test
    @DisplayName("Crea un comentario correctamente con autenticación válida")
    void creates_comment_successfully() throws Exception {

        String json = """
                {
                  "authorId": "%s",
                  "postId": "%s",
                  "content": "Nice post!"
                }
                """.formatted(
                author.id().value(),
                post.id().value()
        );

        mockMvc.perform(
                        post("/api/comments")
                                .with(httpBasic("test@example.com", "password123"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());

        // Validar que el comentario existe usando SOLO los métodos del puerto
        Optional<Comment> savedOpt =
                commentRepository.findByPostId(post.id()).stream().findFirst();

        assertThat(savedOpt).isPresent();

        Comment saved = savedOpt.get();

        assertThat(saved.content().value()).isEqualTo("Nice post!");

        // CORRECCIÓN APLICADA: comparar UUID con UUID
        assertThat(saved.authorId().value())
                .isEqualTo(UUID.fromString(author.id().value().toString()));

        assertThat(saved.postId().value()).isEqualTo(post.id().value());
        assertThat(saved.id()).isNotNull();
        assertThat(saved.createdAt()).isNotNull();
    }
}
