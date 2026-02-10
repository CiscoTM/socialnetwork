package com.example.socialnetwork.infrastructure.security;

import com.example.socialnetwork.infrastructure.security.jwt.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;


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

            // ❗ No autenticamos aquí
            // Dejamos que @WithMockUser haga su trabajo
            chain.doFilter(request, response);
            return null;
        }).when(filter).doFilter(any(), any(), any());

        return filter;
    }
}
