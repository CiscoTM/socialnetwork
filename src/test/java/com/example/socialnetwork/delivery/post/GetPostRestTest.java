package com.example.socialnetwork.delivery.post;

import com.example.socialnetwork.application.post.GetPostByIdQuery;
import com.example.socialnetwork.application.post.GetPostByIdResponse;
import com.example.socialnetwork.application.post.exception.PostNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostRestController.class)
public class GetPostRestTest {
    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private PostApplicationService service;


    @Test
    void shouldReturnPost()throws Exception{
        UUID id = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        when(service.getPostById(any())).thenReturn(
                new GetPostByIdResponse(
                        id,
                        authorId,
                        "Hello",
                        Instant.now())
        );
        mvc.perform(get("/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(id.toString()))
                .andExpect(jsonPath("$.authorId").value(authorId.toString()))
                .andExpect(jsonPath("$.content").value("Hello"));
    }
    @Test
    void shouldReturn404WhenPostNotFound() throws Exception{
        when(service.getPostById(any())).thenThrow(new PostNotFoundException("Not found"));

        mvc.perform(get("/posts/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
