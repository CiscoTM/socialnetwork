package com.example.socialnetwork.delivery.interactions.like;

import com.example.socialnetwork.application.interactions.like.LikePostService;
import com.example.socialnetwork.delivery.interactions.like.controller.LikeController;
import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.interactions.like.LikeId;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {

    private MockMvc mockMvc;
    @Mock private LikePostService service;

    @InjectMocks
    private LikeController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void creates_like_successfully() throws Exception{
        Like like = Like.create(
                LikeId.generate(),
                AuthorId.of(UUID.randomUUID()),
                PostId.generate()
        );
        when(service.execute(any(), any()))
                .thenReturn(like);
        mockMvc.perform(post("/api/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "authorId": "00000000-0000-0000-0000-000000000001",
                          "postId": "00000000-0000-0000-0000-000000000002"
                        }
                        """))
                .andExpect(status().isOk());
    }
}
