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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import support.PostgresTestContainer;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.liquibase.enabled=true"
})
@Import(FollowRepositoryJpaAdapter.class)
class FollowRepositoryJpaAdapterTest extends PostgresTestContainer {

    @Autowired private FollowRepositoryJpaAdapter repository;
    @Autowired private JpaFollowRepository jpaRepository;
    @Autowired private JpaUserRepository userRepository;

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
                "password123",
                "USER"
        ));

        userRepository.save(new UserEntity(
                followedIdValue,
                "followed@test.com",
                "Followed",
                Instant.now(),
                "followed@test.com",
                "password123",
                "USER"
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
        Follow follow = Follow.create(
                FollowId.generate(),
                AuthorId.of(UUID.randomUUID()),
                AuthorId.of(UUID.randomUUID())
        );

        assertThatThrownBy(() -> repository.save(follow))
                .isInstanceOf(Exception.class);
    }
}
