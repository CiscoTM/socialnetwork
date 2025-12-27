package com.example.socialnetwork.application.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.exceptions.DuplicateFollowException;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.interactions.follow.ports.FollowRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class FollowUserServiceTest {
    private final FollowRepository repository = mock(FollowRepository.class);
    private final FollowUserService service = new FollowUserService(repository);

    @Test
    void creates_follow_successfully(){
        AuthorId follower = AuthorId.of(UUID.randomUUID());
        AuthorId followed = AuthorId.of(UUID.randomUUID());

        Follow result = service.execute(follower, followed);

        assertThat(result.followerId().value()).isEqualTo(follower.value());
        assertThat(result.followedId().value()).isEqualTo(followed.value());
        verify(repository).save(any(Follow.class));
    }
    @Test
    void self_follow_throws_exception(){
        AuthorId user = AuthorId.of(UUID.randomUUID());

        assertThatThrownBy(() -> service.execute(user, user))
                .isInstanceOf(SelfFollowNotAllowedException.class);
    }
    @Test
    void duplicate_follow_throws_exception(){
        AuthorId follower = AuthorId.of(UUID.randomUUID());
        AuthorId followed = AuthorId.of(UUID.randomUUID());

        when(repository.findByUsers(follower,followed))
                .thenReturn(Optional.of(mock(Follow.class)));
        assertThatThrownBy(() -> service.execute(follower, followed))
                .isInstanceOf(DuplicateFollowException.class);
    }
}
