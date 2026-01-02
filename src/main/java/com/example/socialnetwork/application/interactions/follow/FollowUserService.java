package com.example.socialnetwork.application.interactions.follow;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.interactions.follow.FollowId;
import com.example.socialnetwork.domain.interactions.follow.exceptions.DuplicateFollowException;
import com.example.socialnetwork.domain.interactions.follow.exceptions.SelfFollowNotAllowedException;
import com.example.socialnetwork.domain.interactions.follow.ports.FollowRepository;
import com.example.socialnetwork.domain.post.AuthorId;

import java.util.UUID;

public class FollowUserService {
    private final FollowRepository followRepository;

    public FollowUserService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Follow execute(AuthorId follower, AuthorId followed){
        if(follower.value().equals(followed.value())){
            throw new SelfFollowNotAllowedException("A user cannot follow themselves");
        }
        followRepository.findByFollowerAndFollowed(follower, followed)
                .ifPresent( existing -> {
                    throw new DuplicateFollowException("User already follows this user");
        });
        Follow follow = Follow.create(FollowId.generate(), follower, followed);
        followRepository.save(follow);
       return follow;
    }
}
