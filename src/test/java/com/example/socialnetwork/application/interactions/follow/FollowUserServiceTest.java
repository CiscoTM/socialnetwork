package com.example.socialnetwork.application.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.exceptions.DuplicateFollowException;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.interactions.follow.ports.FollowRepository;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class FollowUserServiceTest {

    private final FollowRepository followRepository = mock(FollowRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private final FollowUserService service =
            new FollowUserService(followRepository, userRepository);

    @Test
    void creates_follow_successfully() {
        UserId follower = UserId.of(UUID.randomUUID());
        UserId followed = UserId.of(UUID.randomUUID());

        when(userRepository.findById(follower)).thenReturn(Optional.of(mock(User.class)));
        when(userRepository.findById(followed)).thenReturn(Optional.of(mock(User.class)));

        Follow result = service.execute(follower, followed);

        assertThat(result.followerId()).isEqualTo(follower);
        assertThat(result.followedId()).isEqualTo(followed);
        verify(followRepository).save(any(Follow.class));
    }

    @Test
    void self_follow_throws_exception() {
        UserId same = UserId.of(UUID.randomUUID());

        assertThatThrownBy(() -> service.execute(same, same))
                .isInstanceOf(SelfFollowNotAllowedException.class);
    }

    @Test
    void duplicate_follow_throws_exception() {
        UserId follower = UserId.of(UUID.randomUUID());
        UserId followed = UserId.of(UUID.randomUUID());

        when(userRepository.findById(follower)).thenReturn(Optional.of(mock(User.class)));
        when(userRepository.findById(followed)).thenReturn(Optional.of(mock(User.class)));

        when(followRepository.findByFollowerAndFollowed(follower, followed))
                .thenReturn(Optional.of(mock(Follow.class)));

        assertThatThrownBy(() -> service.execute(follower, followed))
                .isInstanceOf(DuplicateFollowException.class);
    }
}
