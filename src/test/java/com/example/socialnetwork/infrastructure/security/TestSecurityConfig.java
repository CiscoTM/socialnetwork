package com.example.socialnetwork.infrastructure.security;

import com.example.socialnetwork.infrastructure.security.jwt.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

import static org.mockito.Mockito.*;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    @Primary
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws ServletException, IOException {
        JwtAuthenticationFilter filter = mock(JwtAuthenticationFilter.class);

        doAnswer(invocation -> {
            HttpServletRequest request = invocation.getArgument(0);
            HttpServletResponse response = invocation.getArgument(1);
            FilterChain chain = invocation.getArgument(2);

            chain.doFilter(request, response);
            return null;
        }).when(filter).doFilter(any(), any(), any());

        return filter;
    }
}
