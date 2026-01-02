package com.example.socialnetwork.infrastructure.persistence.user;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.infrastructure.persistence.user.adapters.UserRepositoryJpaAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import support.JpaTestBase;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@Import(UserRepositoryJpaAdapter.class)
class UserRepositoryJpaAdapterTest extends JpaTestBase {

    @Autowired
    private UserRepository repository;

    @Test
    void save_and_find_user() {
        // Arrange
        User user = new User(
                new UserId("u1"),
                UserEmail.of("test@example.com"),
                "Test User",
                Instant.now()
        );

        // Act
        repository.save(user);

        // Assert
        User loaded = repository.findById(UserId.of("u1")).orElseThrow();

        assertThat(loaded).isNotNull();
        assertThat(loaded.email().value()).isEqualTo("test@example.com");
    }

    @Test
    void exists_by_email() {
        // Arrange
        User user = new User(
                new UserId("u2"),
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
