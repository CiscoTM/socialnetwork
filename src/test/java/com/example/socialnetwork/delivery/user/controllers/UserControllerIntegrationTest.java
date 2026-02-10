package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.delivery.interactions.exceptions.GlobalExceptionHandler;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRegistrationService userRegistrationService;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    void registers_user_successfully_via_http() throws Exception {
        UserId id = UserId.generate();

        when(userRegistrationService.register(any(), any(), any()))
                .thenReturn(new User(
                        id,
                        UserEmail.of("http-test@example.com"),
                        "Francisco",
                        java.time.Instant.now()
                ));

        String payload = """
                {
                  "id": "%s",
                  "email": "http-test@example.com",
                  "displayName": "Francisco"
                }
                """.formatted(id);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.value().toString()))
                .andExpect(jsonPath("$.email").value("http-test@example.com"))
                .andExpect(jsonPath("$.displayName").value("Francisco"));
    }
}
