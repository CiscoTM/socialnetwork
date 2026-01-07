package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.domain.user.exceptions.UserAlreadyExistsException;
import com.example.socialnetwork.domain.user.ports.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)   // ← DESACTIVA SPRING SECURITY
@ActiveProfiles("test")
class UserControllerErrorHandlingIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRegistrationService userRegistrationService;

    @Test
    void returns_conflict_when_email_already_exists() throws Exception {

        // Simulamos que el servicio lanza la excepción de dominio
        doThrow(new UserAlreadyExistsException("duplicate@example.com"))
                .when(userRegistrationService)
                .register(any(),any(),any());

        String payload = """
            {
                "id": "u-2",
                "email": "duplicate@example.com",
                "displayName": "Another"
            }
            """;

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("A user with email 'duplicate@example.com' already exists"));
    }
}
