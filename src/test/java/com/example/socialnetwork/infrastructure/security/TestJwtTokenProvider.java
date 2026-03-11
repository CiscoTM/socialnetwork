package com.example.socialnetwork.infrastructure.security;

import com.example.socialnetwork.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestJwtTokenProvider {

    @Bean
    @Primary
    public JwtTokenProvider jwtTokenProvider() {
        String secret = "test-secret-test-secret-test-secret-test-secret";
        return new JwtTokenProvider(secret);
    }
}
