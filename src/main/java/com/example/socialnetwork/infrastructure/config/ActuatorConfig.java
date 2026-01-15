package com.example.socialnetwork.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.Map;

@Configuration
public class ActuatorConfig {

    @Bean
    public HealthContributor customHealthIndicators() {
        return CompositeHealthContributor.fromMap(
                Map.of(
                        "customIndicator", (HealthIndicator) () -> Health.up().withDetail("status", "OK").build()
                )
        );
    }
}

