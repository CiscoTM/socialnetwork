package com.example.socialnetwork.integration.auth;

import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import com.example.socialnetwork.infrastructure.persistence.user.jpa.JpaUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RefreshTokenIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanDb() {
        jpaUserRepository.deleteAll();
    }

    private String extractRefreshToken(String json) throws Exception {
        JsonNode node = new ObjectMapper().readTree(json);
        return node.get("refreshToken").asText();
    }

    @Test
    void refresh_token_flow() throws Exception {

        UserEntity admin = new UserEntity(
                UUID.randomUUID(),
                "admin@example.com",
                "Admin",
                Instant.now(),
                "admin",
                passwordEncoder.encode("password"),
                "ADMIN"
        );

        jpaUserRepository.save(admin);

        String loginBody = """
                {
                  "username": "admin",
                  "password": "password"
                }
                """;

        String loginResponse = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginBody)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String refreshToken = extractRefreshToken(loginResponse);

        mockMvc.perform(
                post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\":\"" + refreshToken + "\"}")
        ).andExpect(status().isOk());
    }
}
