package com.example.socialnetwork.delivery.interactions.follow;

import com.example.socialnetwork.application.interactions.follow.FollowUserService;
import com.example.socialnetwork.delivery.interactions.follow.controllers.FollowController;
import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.post.AuthorId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
public class FollowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private FollowUserService service;

    @Test
    void creates_follow_successfully() throws Exception{
        Follow follow = Follow.create(
                FollowId.generate(),
                AuthorId.of(UUID.randomUUID()),
                AuthorId.of(UUID.randomUUID())
        );
        when(service.execute(any(),any()))
                .thenReturn(follow);

        mockMvc.perform(post("/api/follows")
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
    void self_follow_returns_bad_request()throws Exception{
        UUID same = UUID.randomUUID();
        when(service.execute(AuthorId.of(same), AuthorId.of(same)))
                .thenThrow(new SelfFollowNotAllowedException("A user cannot follow themselves"));

        mockMvc.perform(post("/api/follows")
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
