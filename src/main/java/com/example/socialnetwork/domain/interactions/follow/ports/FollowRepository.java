package com.example.socialnetwork.domain.interactions.follow.ports;

import com.example.socialnetwork.domain.interactions.follow.Follow;
import com.example.socialnetwork.domain.post.AuthorId;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FollowRepository {
    void save(Follow follow);
    Optional<Follow>findByFollowerAndFollowed(AuthorId followerId, AuthorId followedId);
}
