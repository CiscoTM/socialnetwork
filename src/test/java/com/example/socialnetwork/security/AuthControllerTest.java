package com.example.socialnetwork.security;

import com.example.socialnetwork.application.auth.RefreshTokenService;
import com.example.socialnetwork.delivery.auth.AuthController;
import com.example.socialnetwork.application.auth.AuthenticateUserService;
import com.example.socialnetwork.delivery.auth.TokenResponse;
import com.example.socialnetwork.infrastructure.security.jwt.JwtTokenProvider;
import com.example.socialnetwork.security.config.SecurityIntegrationTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)          // desactiva filtros de seguridad
@Import(SecurityIntegrationTestConfig.class)       // sobrescribe JwtAuthenticationFilter y JwtTokenProvider
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.security.enabled=true")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticateUserService authenticateUserService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @Test
    void login_returns_token() throws Exception {

        TokenResponse fakeTokens = new TokenResponse("fakeAccess", "fakeRefresh");

        when(authenticateUserService.authenticate("admin@example.com", "password123"))
                .thenReturn(fakeTokens);

        String body = """
                {
                  "username": "admin@example.com",
                  "password": "password123"
                }
                """;

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());
    }
}
