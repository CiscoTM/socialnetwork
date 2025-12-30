package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserRegistrationService userRegistrationService;

    @Test
    void registers_user_successfully_via_http() throws Exception {

        // Mock del servicio de registro
        when(userRegistrationService.register(any(),any(),any())).thenReturn(
                new User(
                        UserId.of("u-1"),
                        UserEmail.of("http-test@example.com"),
                        "Francisco",
                        java.time.Instant.now()
                )
        );

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
    }
}
