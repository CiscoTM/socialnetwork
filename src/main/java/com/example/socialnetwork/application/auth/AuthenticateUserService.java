package com.example.socialnetwork.application.auth;

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

    public String authenticate(String username, String rawPassword) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        System.out.println("RAW = " + rawPassword);
        System.out.println("HASH = " + userDetails.getPassword());
        System.out.println("MATCHES = " + passwordEncoder.matches(rawPassword, userDetails.getPassword()));

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UserEmail email;
        try {
            email = UserEmail.of(userDetails.getUsername());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid credentials");
        }

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));


        return jwtTokenProvider.generateToken(user.email().value());
    }
}
