package com.example.socialnetwork.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.infrastructure.security.jwt.JwtTokenProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(properties = {
//        "spring.liquibase.enabled=false",
//        "spring.datasource.url=jdbc:h2:mem:testdb",
//        "spring.jpa.hibernate.ddl-auto=none"
//})
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
class JwtSecurityIntegrationTest {

//    @Autowired private MockMvc mockMvc;
//    @Autowired private ObjectMapper objectMapper;
//
//    @MockBean private AuthenticationManager authenticationManager;
//    @MockBean private UserDetailsService userDetailsService;
//    @MockBean private PasswordEncoder passwordEncoder;
//    @MockBean private JwtTokenProvider jwtTokenProvider;
//    @MockBean private UserRepository userRepository;
//    @MockBean private UserRegistrationService userRegistrationService;
//
//    @Test
//    void login_and_access_protected_endpoint() throws Exception {
//
//        String email = "admin@example.com";
//        String rawPassword = "password123";
//        String encodedPassword = "$2a$10$encoded";
//        String fakeToken = "FAKE.JWT.TOKEN";
//
//        UserDetails mockUser = org.springframework.security.core.userdetails.User
//                .withUsername(email)
//                .password(encodedPassword)
//                .roles("USER")
//                .build();
//
//        User domainUser = User.restore(
//                UserId.of(UUID.randomUUID()),
//                UserEmail.of(email),
//                "AdminUser",
//                Instant.now()
//        );
//
//        when(userDetailsService.loadUserByUsername(email)).thenReturn(mockUser);
//        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
//        when(userRepository.findByEmail(UserEmail.of(email))).thenReturn(Optional.of(domainUser));
//        when(jwtTokenProvider.generateToken(email)).thenReturn(fakeToken);
//        when(jwtTokenProvider.validateAndGetSubject(fakeToken)).thenReturn(email);
//
//        String loginBody = """
//            {
//              "username": "admin@example.com",
//              "password": "password123"
//            }
//            """;
//
//        String loginResponse = mockMvc.perform(
//                        post("/auth/login")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(loginBody)
//                )
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        JsonNode json = objectMapper.readTree(loginResponse);
//        String token = json.get("token").asText();
//
//        mockMvc.perform(
//                        get("/users/me")
//                                .header("Authorization", "Bearer " + token)
//                )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void access_protected_endpoint_without_token_returns_401() throws Exception {
//        mockMvc.perform(get("/users/me"))
//                .andExpect(status().isUnauthorized());
//    }
}
