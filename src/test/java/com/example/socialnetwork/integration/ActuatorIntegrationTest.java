package com.example.socialnetwork.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import support.RestIntegrationTestBase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ActuatorIntegrationTest extends RestIntegrationTestBase {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void healthEndpointShouldReturnUp() {
        webTestClient
                .get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }

    @Test
    void livenessEndpointShouldReturnUp() {
        webTestClient
                .get()
                .uri("/actuator/health/liveness")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void readinessEndpointShouldReturnUp() {
        webTestClient
                .get()
                .uri("/actuator/health/readiness")
                .exchange()
                .expectStatus().isOk();
    }
}
