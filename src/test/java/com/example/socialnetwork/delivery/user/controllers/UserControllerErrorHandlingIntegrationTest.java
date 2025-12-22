package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerErrorHandlingIntegrationTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final UserRepository repository;

    @Autowired
    public UserControllerErrorHandlingIntegrationTest(MockMvc mockMvc, UserRepository repository) {
        this.mockMvc = mockMvc;
        this.repository = repository;
    }

    @Test
    void returns_conflict_when_email_already_exists()throws Exception{
        repository.save(new User(
                UserId.of("u-1"),
                UserEmail.of("duplicate@example.com"),
                "Francisco",
                Instant.now()
        ));
        String payload = """
            {\s
                "id": "u-2",
                 "email": "duplicate@example.com",
                  "displayName": "Another"\s
              }\s
                 \s""";

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("A user with email 'duplicate@example.com' already exists"));
    }
}
