package com.example.socialnetwork.delivery.interactions.follow;

import com.example.socialnetwork.delivery.interactions.follow.dtos.FollowResponse;
import com.example.socialnetwork.domain.interactions.follow.Follow;

public class FollowMapper {
    public static FollowResponse toResponse(Follow follow){
        return new FollowResponse(
                follow.id().value(),
                follow.followerId().value(),
                follow.followedId().value(),
                follow.createdAt()
        );
    }
}
