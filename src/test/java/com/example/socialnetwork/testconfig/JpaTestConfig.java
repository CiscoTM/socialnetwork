package com.example.socialnetwork.testconfig;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.socialnetwork.infrastructure.persistence.post.jpa")
@ComponentScan("com.example.socialnetwork.infrastructure.persistence.post")
public class JpaTestConfig {
}
