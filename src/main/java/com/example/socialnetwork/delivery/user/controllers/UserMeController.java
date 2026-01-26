package com.example.socialnetwork.delivery.user.controllers;

import com.example.socialnetwork.delivery.user.dtos.UserResponse;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@ConditionalOnProperty(
        name = "spring.security.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class UserMeController {

    private final UserRepository userRepository;

    public UserMeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {

        // El JWT contiene el username como subject (debería ser el email)
        String username = authentication.getName();

        return userRepository.findByEmail(UserEmail.of(username))
                .map(UserResponse::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
