package com.example.socialnetwork.delivery.interactions.like;

import com.example.socialnetwork.delivery.interactions.like.dtos.LikeResponse;
import com.example.socialnetwork.domain.interactions.like.Like;

public class LikeMapper {
    public static LikeResponse toResponse(Like like){
        return new LikeResponse(
                like.id().value(),
                like.authorId().value(),
                like.postId().value(),
                like.createdAt()
        );
    }
}
