package com.example.socialnetwork.security.config;

import com.example.socialnetwork.infrastructure.security.jwt.JwtAuthenticationFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class SecurityIntegrationTestConfig {

    @Bean
    @Primary
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return mock(JwtAuthenticationFilter.class);
    }
}
