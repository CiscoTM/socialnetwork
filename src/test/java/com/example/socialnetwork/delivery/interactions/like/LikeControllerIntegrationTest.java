package com.example.socialnetwork.delivery.interactions.like;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "user@example.com")
    void like_post_successfully() throws Exception {

        // 1. Crear usuario
        User user = User.create(
                UserId.of(UUID.randomUUID()),
                UserEmail.of("user@example.com"),
                "User"
        );
        userRepository.save(user);

        // 2. Crear post
        AuthorId authorId = AuthorId.of(user.id().value());
        PostContent content = PostContent.of("Contenido del post");
        Post post = Post.create(authorId, content);
        postRepository.save(post);

        // 3. Like request
        String json = """
    {
        "authorId": "%s",
        "postId": "%s"
    }
    """.formatted(user.id().value(), post.id().value());

        mockMvc.perform(
                        post("/api/likes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());
    }


}
