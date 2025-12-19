package com.example.socialnetwork.testUtil;

import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
}

