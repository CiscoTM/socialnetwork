package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.testUtil.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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

