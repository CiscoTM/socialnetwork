package com.example.socialnetwork.delivery.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.ports.CommentRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;

import com.example.socialnetwork.infrastructure.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import support.RestIntegrationTestBase;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class CommentControllerTest extends RestIntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User author;
    private Post post;

    @BeforeEach
    void setup() {

        author = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("test@example.com"),
                "Test User"
        );
        userRepository.save(author);

        post = Post.create(
                AuthorId.of(author.id().value()),
                PostContent.of("Post de prueba")
        );
        postRepository.save(post);
    }

    @Test
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

        Optional<Comment> savedOpt =
                commentRepository.findByPostId(post.id()).stream().findFirst();

        assertThat(savedOpt).isPresent();
    }
}
