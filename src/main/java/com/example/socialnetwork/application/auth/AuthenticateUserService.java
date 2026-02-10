package com.example.socialnetwork.application.auth;

import com.example.socialnetwork.delivery.auth.TokenResponse;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile({"prod", "docker", "test"})
public class AuthenticateUserService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthenticateUserService(JwtTokenProvider jwtTokenProvider,
                                   UserDetailsService userDetailsService,
                                   PasswordEncoder passwordEncoder,
                                   UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public TokenResponse authenticate(String username, String rawPassword) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UserEmail email = UserEmail.of(userDetails.getUsername());

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String accessToken = jwtTokenProvider.generateAccessToken(user.email().value());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.email().value());

        return new TokenResponse(accessToken, refreshToken);
    }

}
