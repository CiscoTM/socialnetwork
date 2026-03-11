package com.example.socialnetwork.delivery.interactions.follow;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "user@example.com")
    void follow_user_successfully() throws Exception {

        User follower = User.create(
                UserId.of(UUID.fromString("11111111-1111-1111-1111-111111111111")),
                UserEmail.of("user@example.com"),
                "User"
        );

        User followed = User.create(
                UserId.of(UUID.fromString("22222222-2222-2222-2222-222222222222")),
                UserEmail.of("other@example.com"),
                "Other"
        );

        userRepository.save(follower);
        userRepository.save(followed);

        String json = """
    {
      "followerId": "11111111-1111-1111-1111-111111111111",
      "followedId": "22222222-2222-2222-2222-222222222222"
    }
    """;

        mockMvc.perform(
                post("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());
    }



}
