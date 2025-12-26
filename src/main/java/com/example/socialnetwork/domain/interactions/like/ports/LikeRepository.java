package com.example.socialnetwork.domain.interactions.like.ports;

import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;

import java.util.Optional;

public interface LikeRepository {
    void save(Like like);
    Optional<Like>findByAuthorAndPost(AuthorId authorId, PostId postId);
}
