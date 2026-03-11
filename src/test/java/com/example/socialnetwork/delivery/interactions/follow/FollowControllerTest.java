package com.example.socialnetwork.delivery.interactions.follow;

import com.example.socialnetwork.application.interactions.follow.FollowUserService;
import com.example.socialnetwork.delivery.interactions.follow.controllers.FollowController;
import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Base64;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FollowControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FollowUserService service;

    @InjectMocks
    private FollowController controller;

    @RestControllerAdvice
    static class GlobalExceptionHandler {
        @ExceptionHandler(SelfFollowNotAllowedException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        void handleSelfFollow() {}
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private String auth() {
        return "Basic " + Base64.getEncoder()
                .encodeToString("admin:password".getBytes());
    }

    @Test
    void creates_follow_successfully() throws Exception {
        Follow follow = Follow.create(
                FollowId.generate(),
                UserId.of(UUID.randomUUID()),
                UserId.of(UUID.randomUUID())
        );

        when(service.execute(any(), any())).thenReturn(follow);

        String json = """
        {
          "followerId": "00000000-0000-0000-0000-000000000001",
          "followedId": "00000000-0000-0000-0000-000000000002"
        }
        """;

        mockMvc.perform(post("/api/follows")
                        .with(csrf())
                        .header("Authorization", auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void self_follow_returns_bad_request() throws Exception {
        UUID same = UUID.randomUUID();

        when(service.execute(UserId.of(same), UserId.of(same)))
                .thenThrow(new SelfFollowNotAllowedException("A user cannot follow themselves"));

        String json = """
        {
          "followerId": "%s",
          "followedId": "%s"
        }
        """.formatted(same, same);

        mockMvc.perform(post("/api/follows")
                        .with(csrf())
                        .header("Authorization", auth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
