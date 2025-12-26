package com.example.socialnetwork.domain.interactions.like;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.assertj.core.api.Assertions.*;


public class LikeTest {
    @Test
    void create_like_successfully(){
        LikeId likeId = LikeId.generate();
        AuthorId authorId = AuthorId.of(UUID.randomUUID());
        PostId postId = PostId.generate();

        Like like = Like.create(likeId, authorId, postId);

        assertThat(like.id()).isEqualTo(likeId);
        assertThat(like.authorId()).isEqualTo(authorId);
        assertThat(like.postId()).isEqualTo(postId);
        assertThat(like.createdAt()).isNotNull();
    }
}
