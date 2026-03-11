package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.delivery.shared.exceptions.GlobalExceptionHandler;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        // Usamos un UUID real para que UUID.fromString no falle
        UUID uuid = UUID.randomUUID();
        String rawId = uuid.toString();
        String email = "http-test@example.com";
        String displayName = "Francisco";

        // 1. EL MOCK: Debe coincidir con los tipos que recibe el service en el Controller
        // El Controller envía (String, String, String)
        when(userRegistrationService.register(anyString(), anyString(), anyString()))
                .thenReturn(new User(
                        new UserId(uuid), // UserId de dominio
                        UserEmail.of(email),
                        displayName,
                        java.time.Instant.now()
                ));

        // 2. EL PAYLOAD: JSON plano que Jackson convertirá a tu Record
        String payload = """
            {
              "id": "%s",
              "email": "%s",
              "displayName": "%s"
            }
            """.formatted(rawId, email, displayName);

        // 3. LA EJECUCIÓN
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(rawId))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.displayName").value(displayName));
    }
}
