package com.example.socialnetwork.domain.interactions.follow.ports;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.user.UserId;

import java.util.Optional;

public interface FollowRepository {

    void save(Follow follow);

    Optional<Follow> findByFollowerAndFollowed(UserId follower, UserId followed);
}
