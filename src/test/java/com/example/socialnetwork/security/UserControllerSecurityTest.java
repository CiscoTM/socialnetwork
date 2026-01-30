package com.example.socialnetwork.security;

import com.example.socialnetwork.delivery.user.controllers.UserMeController;
import com.example.socialnetwork.security.config.SecurityIntegrationTestConfig;
import com.example.socialnetwork.infrastructure.security.jwt.JwtTokenProvider;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.application.auth.AuthenticateUserService;
import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.infrastructure.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserMeController.class)
@Import(SecurityIntegrationTestConfig.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.security.enabled=true")
class UserControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CustomUserDetailsService customUserDetailsService;
    @MockBean private AuthenticateUserService authenticateUserService;
    @MockBean private UserRepository userRepository;
    @MockBean private UserRegistrationService userRegistrationService;

    @MockBean private JwtTokenProvider jwtTokenProvider;

    @Test
    void users_me_with_valid_token_returns_200() throws Exception {

        String email = "admin@example.com";
        String fakeToken = "FAKE.JWT.TOKEN";

        User domainUser = User.restore(
                UserId.of(UUID.randomUUID()),
                UserEmail.of(email),
                "AdminUser",
                Instant.now()
        );

        when(jwtTokenProvider.validateAndGetSubject(fakeToken)).thenReturn(email);
        when(userRepository.findByEmail(UserEmail.of(email))).thenReturn(Optional.of(domainUser));

        mockMvc.perform(
                get("/users/me")
                        .header("Authorization", "Bearer " + fakeToken)
        ).andExpect(status().isOk());
    }

    @Test
    void users_me_without_token_returns_401() throws Exception {
        mockMvc.perform(get("/users/me"))
                .andExpect(status().isUnauthorized());
    }
}
