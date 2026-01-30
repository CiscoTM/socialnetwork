package com.example.socialnetwork.infrastructure.persistence.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.infrastructure.persistence.interactions.follow.adapters.FollowRepositoryJpaAdapter;
import com.example.socialnetwork.infrastructure.persistence.interactions.follow.jpa.JpaFollowRepository;
import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import com.example.socialnetwork.infrastructure.persistence.user.jpa.JpaUserRepository;
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

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(FollowRepositoryJpaAdapter.class)
class FollowRepositoryJpaAdapterTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);

        registry.add("spring.liquibase.enabled", () -> true);
        registry.add("spring.liquibase.url", postgres::getJdbcUrl);
        registry.add("spring.liquibase.user", postgres::getUsername);
        registry.add("spring.liquibase.password", postgres::getPassword);
    }

    @Autowired
    private FollowRepositoryJpaAdapter repository;

    @Autowired
    private JpaFollowRepository jpaRepository;

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    void save_follow_successfully() {
        UUID followerIdValue = UUID.randomUUID();
        UUID followedIdValue = UUID.randomUUID();

        userRepository.save(new UserEntity(
                followerIdValue,
                "follower@test.com",
                "Follower",
                Instant.now(),
                "follower@test.com",
                "USER",
                ""
        ));

        userRepository.save(new UserEntity(
                followedIdValue,
                "followed@test.com",
                "Followed",
                Instant.now(),
                "followed@test.com",
                "USER",
                ""
        ));

        FollowId id = FollowId.generate();

        Follow follow = Follow.create(
                id,
                AuthorId.of(followerIdValue),
                AuthorId.of(followedIdValue)
        );

        repository.save(follow);

        var entity = jpaRepository.findById(id.value()).orElseThrow();

        assertThat(entity.getFollower()).isEqualTo(followerIdValue);
        assertThat(entity.getFollowed()).isEqualTo(followedIdValue);
    }


    @Test
    void saving_follow_with_nonexistent_user_should_fail() {
        // Arrange
        Follow follow = Follow.create(
                FollowId.generate(),
                AuthorId.of(UUID.randomUUID()),      // follower inexistente
                AuthorId.of(UUID.randomUUID())       // followed inexistente
        );

        // Assert
        assertThatThrownBy(() -> repository.save(follow))
                .isInstanceOf(Exception.class);
    }

}
