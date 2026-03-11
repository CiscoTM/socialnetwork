package com.example.socialnetwork.delivery.auth.controllers;

import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import com.example.socialnetwork.infrastructure.persistence.user.jpa.JpaUserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import support.IntegrationTestBase;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        jpaUserRepository.deleteAll();

        UserEntity entity = new UserEntity(
                UUID.randomUUID(),
                "user@example.com",
                "User Example",
                Instant.now(),
                "user@example.com",
                passwordEncoder.encode("password123"),
                "USER"
        );

        userRepository.saveEntity(entity);
    }



    @Test
    void login_returns_tokens() throws Exception {
        String json = """
        {
            "username": "user@example.com",
            "password": "password123"
        }
        """;

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void refresh_returns_new_access_token() throws Exception {

        // 1. Login real
        String loginJson = """
    {
        "username": "user@example.com",
        "password": "password123"
    }
    """;

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String refreshToken = JsonPath.read(
                loginResult.getResponse().getContentAsString(),
                "$.refreshToken"
        );

        // 2. Usar refresh token real
        String refreshJson = """
    {
        "refreshToken": "%s"
    }
    """.formatted(refreshToken);

        mockMvc.perform(
                        post("/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(refreshJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

}
