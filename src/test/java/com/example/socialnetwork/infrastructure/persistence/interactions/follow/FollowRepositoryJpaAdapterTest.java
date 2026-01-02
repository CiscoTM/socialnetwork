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
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import support.JpaTestBase;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@Import(FollowRepositoryJpaAdapter.class)
class FollowRepositoryJpaAdapterTest extends JpaTestBase {
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
                followerIdValue.toString(),
                "follower@test.com",
                "Follower",
                Instant.now()
        ));

        userRepository.save(new UserEntity(
                followedIdValue.toString(),
                "followed@test.com",
                "Followed",
                Instant.now()
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
