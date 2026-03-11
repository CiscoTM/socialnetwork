package com.example.socialnetwork.application.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.exceptions.DuplicateFollowException;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.interactions.follow.ports.FollowRepository;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowUserService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowUserService(FollowRepository followRepository,
                             UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public Follow execute(UserId followerId, UserId followedId) {

        if (followerId.equals(followedId)) {
            throw new SelfFollowNotAllowedException("A user cannot follow themselves");
        }

        if (userRepository.findById(followerId).isEmpty()) {
            throw new IllegalArgumentException("Follower does not exist: " + followerId.value());
        }

        if (userRepository.findById(followedId).isEmpty()) {
            throw new IllegalArgumentException("Followed user does not exist: " + followedId.value());
        }

        followRepository.findByFollowerAndFollowed(followerId, followedId)
                .ifPresent(existing -> {
                    throw new DuplicateFollowException("User already follows this user");
                });

        Follow follow = Follow.create(FollowId.generate(), followerId, followedId);
        followRepository.save(follow);

        return follow;
    }
}
