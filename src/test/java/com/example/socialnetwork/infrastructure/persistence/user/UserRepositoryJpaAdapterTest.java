package com.example.socialnetwork.infrastructure.persistence.user;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.infrastructure.persistence.user.adapters.UserRepositoryJpaAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserRepositoryJpaAdapter.class)
class UserRepositoryJpaAdapterTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

    @Autowired
    private UserRepository repository;

    @Test
    void save_and_find_user() {
        UserId id = UserId.generate();
        // Arrange
        User user = new User(
                id,
                UserEmail.of("test@example.com"),
                "Test User",
                Instant.now()
        );

        // Act
        repository.save(user);

        // Assert
        User loaded = repository.findById(id).orElseThrow();

        assertThat(loaded).isNotNull();
        assertThat(loaded.email().value()).isEqualTo("test@example.com");
    }

    @Test
    void exists_by_email() {
        UserId id = UserId.generate();
        // Arrange
        User user = new User(
                id,
                UserEmail.of("unique@example.com"),
                "Another User",
                Instant.now()
        );

        repository.save(user);

        // Assert
        assertThat(repository.existsByEmail(UserEmail.of("unique@example.com"))).isTrue();
        assertThat(repository.existsByEmail(UserEmail.of("other@example.com"))).isFalse();
    }
}
