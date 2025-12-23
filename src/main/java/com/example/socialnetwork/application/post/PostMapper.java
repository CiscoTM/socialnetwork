package com.example.socialnetwork.application.post;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;

public class PostMapper {

    public static Post toDomain(CreatePostCommand command){
        return Post.create(
                AuthorId.of(command.authorId()),
                PostContent.of(command.content())
        );
    }
    public static CreatePostResponse toCreateResponse(Post post){
        return new CreatePostResponse(
                post.id().value(),
                post.authorId().value(),
                post.content().value(),
                post.createdAt()
        );
    }
    public static GetPostByIdResponse toGetResponse(Post post){
        return new GetPostByIdResponse(
                post.id().value(),
                post.authorId().value(),
                post.content().value(),
                post.createdAt()
        );
    }
}
