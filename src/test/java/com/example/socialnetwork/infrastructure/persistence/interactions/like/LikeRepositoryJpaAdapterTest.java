package com.example.socialnetwork.infrastructure.persistence.interactions.like;

import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.interactions.like.LikeId;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.infrastructure.persistence.interactions.like.adapters.LikeRepositoryJpaAdapter;
import com.example.socialnetwork.infrastructure.persistence.interactions.like.jpa.JpaLikeRepository;
import com.example.socialnetwork.infrastructure.persistence.post.PostEntity;
import com.example.socialnetwork.infrastructure.persistence.post.jpa.JpaPostRepository;
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

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(LikeRepositoryJpaAdapter.class)
public class LikeRepositoryJpaAdapterTest {

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
    private LikeRepositoryJpaAdapter repository;

    @Autowired
    private JpaLikeRepository jpaRepository;

    @Autowired
    private JpaPostRepository postRepository;

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    void save_like_successfully() {
        UUID postIdValue = UUID.randomUUID();
        UUID userIdValue = UUID.randomUUID();

        postRepository.save(new PostEntity(
                postIdValue,
                userIdValue,
                "Test content",
                Instant.now()
        ));

        userRepository.save(new UserEntity(
                userIdValue,
                "user@test.com",
                "User Name",
                Instant.now()
        ));

        LikeId id = LikeId.generate();

        Like like = Like.create(
                id,
                AuthorId.of(userIdValue),
                PostId.of(postIdValue)
        );

        repository.save(like);

        var entity = jpaRepository.findById(id.value()).orElseThrow();

        assertThat(entity.getPostId()).isEqualTo(postIdValue);
        assertThat(entity.getUserId()).isEqualTo(userIdValue);
    }

    @Test
    void saving_like_with_nonexistent_post_should_fail() {
        Like like = Like.create(
                LikeId.generate(),
                AuthorId.of(UUID.randomUUID()),  // user inexistente
                PostId.of(UUID.randomUUID())     // post inexistente
        );

        assertThatThrownBy(() -> repository.save(like))
                .isInstanceOf(Exception.class);
    }

}


