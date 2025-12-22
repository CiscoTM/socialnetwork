package com.example.socialnetwork.domain.post.ports;

import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostId;

import java.util.Optional;

public interface PostRepository {
    void save(Post post);
    Optional<Post>findById(PostId id);
    void deleteById(PostId id);
}
