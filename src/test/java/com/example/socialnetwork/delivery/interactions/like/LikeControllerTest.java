package com.example.socialnetwork.delivery.interactions.like;

import com.example.socialnetwork.application.interactions.like.LikePostService;
import com.example.socialnetwork.delivery.interactions.like.controller.LikeController;
import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.interactions.like.LikeId;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(LikeController.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private LikePostService service;

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
