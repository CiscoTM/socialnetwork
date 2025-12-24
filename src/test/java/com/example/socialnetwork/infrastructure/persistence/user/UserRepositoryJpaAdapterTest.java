package com.example.socialnetwork.infrastructure.persistence.user;

import com.example.socialnetwork.SocialnetworkApplication;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import support.IntegrationTestBase;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
class UserRepositoryJpaAdapterTest extends IntegrationTestBase {

    @Autowired
    private UserRepository repository;

    @Test
    void save_and_find_user() {
        User user = new User(
                new UserId("u1"),
                UserEmail.of("test@example.com"),
                "Test User",
                Instant.now()
        );

        repository.save(user);

        User loaded = repository.findById(UserId.of("u1")).orElseThrow();

        assertThat(loaded).isNotNull();
        assertThat(loaded.email().value()).isEqualTo("test@example.com");
    }
    @Test
    void exists_by_email() {
        User user = new User(
                new UserId("u2"),
                UserEmail.of("unique@example.com")                ,
                "Another User",
                Instant.now()
        );

        repository.save(user);

        assertThat(repository.existsByEmail(UserEmail.of("unique@example.com"))).isTrue();
        assertThat(repository.existsByEmail(UserEmail.of("other@example.com"))).isFalse();
    }
}
