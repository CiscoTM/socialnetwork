package com.example.socialnetwork.infrastructure.security.jwt;

import com.example.socialnetwork.infrastructure.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Test
    void when_valid_bearer_token_then_authentication_is_set() throws ServletException, IOException {
        // given
        String email = "user@example.com";

        JwtTokenProvider tokenProvider = mock(JwtTokenProvider.class);
        CustomUserDetailsService userDetailsService = mock(CustomUserDetailsService.class);

        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(tokenProvider.getUsername(anyString())).thenReturn(email);

        UserDetails userDetails = User.withUsername(email)
                .password("secret")
                .roles("USER")
                .build();

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(tokenProvider, userDetailsService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        // when
        filter.doFilterInternal(request, response, chain);

        // then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName())
                .isEqualTo(email);

        ArgumentCaptor<MockHttpServletRequest> reqCaptor = ArgumentCaptor.forClass(MockHttpServletRequest.class);
        ArgumentCaptor<MockHttpServletResponse> resCaptor = ArgumentCaptor.forClass(MockHttpServletResponse.class);
        verify(chain, times(1)).doFilter(reqCaptor.capture(), resCaptor.capture());
    }

    @Test
    void when_no_bearer_header_then_chain_continues_without_authentication() throws ServletException, IOException {
        JwtTokenProvider tokenProvider = mock(JwtTokenProvider.class);
        CustomUserDetailsService userDetailsService = mock(CustomUserDetailsService.class);

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(tokenProvider, userDetailsService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(request, response, chain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(chain, times(1)).doFilter(any(), any());
    }
}
