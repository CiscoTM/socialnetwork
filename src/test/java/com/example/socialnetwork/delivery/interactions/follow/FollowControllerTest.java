package com.example.socialnetwork.delivery.interactions.follow;

import com.example.socialnetwork.application.interactions.follow.FollowUserService;
import com.example.socialnetwork.delivery.interactions.follow.controllers.FollowController;
import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.infrastructure.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Base64;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(
        controllers = FollowController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = com.example.socialnetwork.infrastructure.security.SecurityConfig.class
        )
)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = true)
class FollowControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FollowUserService service;

    private String auth() {
        return "Basic " + Base64.getEncoder()
                .encodeToString("admin:password".getBytes());
    }

    @Test
    void creates_follow_successfully() throws Exception {
        Follow follow = Follow.create(
                FollowId.generate(),
                AuthorId.of(UUID.randomUUID()),
                AuthorId.of(UUID.randomUUID())
        );

        when(service.execute(any(), any())).thenReturn(follow);

        mockMvc.perform(post("/api/follows")
                        .with(csrf())
                        .header("Authorization", auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "follower":"00000000-0000-0000-0000-000000000001",
                                "followed":"00000000-0000-0000-0000-000000000002"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void self_follow_returns_bad_request() throws Exception {
        UUID same = UUID.randomUUID();

        when(service.execute(AuthorId.of(same), AuthorId.of(same)))
                .thenThrow(new SelfFollowNotAllowedException("A user cannot follow themselves"));

        mockMvc.perform(post("/api/follows")
                        .with(csrf())
                        .header("Authorization", auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "follower":"%s",
                                "followed":"%s"
                                }
                                """.formatted(same, same)))
                .andExpect(status().isBadRequest());
    }
}
