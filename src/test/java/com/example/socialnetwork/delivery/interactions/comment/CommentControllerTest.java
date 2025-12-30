package com.example.socialnetwork.delivery.interactions.comment;

import com.example.socialnetwork.application.interactions.comment.CreateCommentService;
import com.example.socialnetwork.delivery.interactions.comment.controllers.CommentController;
import com.example.socialnetwork.domain.interactions.comment.*;
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

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CreateCommentService service;

    @Test
    void creates_comment_successfully() throws Exception{

        Comment comment = Comment.create(
                CommentId.generate(),
                AuthorId.of(UUID.randomUUID()),
                PostId.generate(),
                CommentContent.of("Nice post!")
                );

        when(service.execute(any(), any(), any()))
                .thenReturn(comment);
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "authorId": "00000000-0000-0000-0000-000000000001",
                          "postId": "00000000-0000-0000-0000-000000000002",
                          "content": "Nice post!"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Nice post!"));
    }
}
