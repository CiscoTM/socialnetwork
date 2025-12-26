package com.example.socialnetwork.domain.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.post.AuthorId;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

public class FollowTest {
    @Test
    void create_follow_successfully(){
        FollowId id = FollowId.generate();
        AuthorId follower = AuthorId.of(UUID.randomUUID());
        AuthorId followed = AuthorId.of(UUID.randomUUID());

        Follow follow = Follow.create(id, follower, followed);

        assertThat(follow.id()).isEqualTo(id);
        assertThat(follow.followerId()).isEqualTo(follower);
        assertThat(follow.followedId()).isEqualTo(followed);
        assertThat(follow.createdAt()).isNotNull();
    }
    @Test
    void self_follow_throws_exception(){
        AuthorId user = AuthorId.of(UUID.randomUUID());

        assertThatThrownBy( () -> Follow.create(FollowId.generate(), user, user))
                .isInstanceOf(SelfFollowNotAllowedException.class);
    }
}
