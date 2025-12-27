package com.example.socialnetwork.application.interactions.like;

import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.interactions.like.LikeId;
import com.example.socialnetwork.domain.interactions.like.exceptions.DuplicateLikeException;
import com.example.socialnetwork.domain.interactions.like.ports.LikeRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;

public class LikePostService {
    private final LikeRepository likeRepository;

    public LikePostService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    public Like execute(AuthorId authorId, PostId postId){
        likeRepository.findByAuthorAndPost(authorId,postId)
                .ifPresent(existing -> {
                    throw new DuplicateLikeException("User already liked this post");
                });
        Like like = Like.create(LikeId.generate(), authorId, postId);
        likeRepository.save(like);
        return like;
    }
}
