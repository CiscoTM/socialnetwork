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
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import support.JpaTestBase;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@Import(LikeRepositoryJpaAdapter.class)
public class LikeRepositoryJpaAdapterTest extends JpaTestBase {
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
                userIdValue.toString(),
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


