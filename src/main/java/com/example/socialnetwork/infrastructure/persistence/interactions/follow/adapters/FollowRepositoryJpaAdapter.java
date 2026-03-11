package com.example.socialnetwork.infrastructure.persistence.interactions.follow.adapters;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.ports.FollowRepository;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.infrastructure.persistence.interactions.follow.FollowEntity;
import com.example.socialnetwork.infrastructure.persistence.interactions.follow.jpa.JpaFollowRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FollowRepositoryJpaAdapter implements FollowRepository {

    private final JpaFollowRepository repository;

    public FollowRepositoryJpaAdapter(JpaFollowRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Follow follow) {
        repository.saveAndFlush(new FollowEntity(
                follow.id().value(),
                follow.followerId().value(),
                follow.followedId().value(),
                follow.createdAt()
        ));
    }

    @Override
    public Optional<Follow> findByFollowerAndFollowed(UserId followerId, UserId followedId) {
        return repository.findByFollowerAndFollowed(followerId.value(), followedId.value())
                .map(entity -> new Follow(
                        FollowId.of(entity.getId()),
                        UserId.of(entity.getFollower()),
                        UserId.of(entity.getFollowed()),
                        entity.getCreatedAt()
                ));
    }
}
