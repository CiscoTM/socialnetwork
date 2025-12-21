package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase(@Autowired DataSource ds) throws Exception {
        try (var conn = ds.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }

    @Test
    void registers_user_successfully_via_http() throws Exception {
        String payload = """
                {
                  "id": "u-1",
                  "email": "http-test@example.com",
                  "displayName": "Francisco"
                }
                """;

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("u-1"))
                .andExpect(jsonPath("$.email").value("http-test@example.com"))
                .andExpect(jsonPath("$.displayName").value("Francisco"));

        assertThat(userRepository.findById(com.example.socialnetwork.domain.user.UserId.of("u-1")))
                .isPresent();
    }
}

