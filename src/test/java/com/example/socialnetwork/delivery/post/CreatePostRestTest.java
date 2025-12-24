package com.example.socialnetwork.delivery.post;

import com.example.socialnetwork.application.post.CreatePostResponse;
import com.example.socialnetwork.application.post.service.PostApplicationService;
import com.example.socialnetwork.delivery.post.controllers.PostRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostRestController.class)
public class CreatePostRestTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PostApplicationService service;


    @Test
    void shouldCreatePost() throws Exception{
        UUID authorId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        when(service.createPost(any())).thenReturn(
                new CreatePostResponse(postId, authorId,"Hello", Instant.now())
        );
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "authorId":"%s",
                            "content":"Hello"
                        }
                        """.formatted(authorId)
                ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId").value(postId.toString()))
                .andExpect(jsonPath("$.authorId").value(authorId.toString()))
                .andExpect(jsonPath("$.content").value("Hello"));
    }
}
