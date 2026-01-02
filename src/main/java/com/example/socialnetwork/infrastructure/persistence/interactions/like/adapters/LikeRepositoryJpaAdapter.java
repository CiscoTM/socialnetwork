package com.example.socialnetwork.infrastructure.persistence.interactions.like.adapters;

import com.example.socialnetwork.domain.interactions.like.Like;
import com.example.socialnetwork.domain.interactions.like.LikeId;
import com.example.socialnetwork.domain.interactions.like.ports.LikeRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.infrastructure.persistence.interactions.like.LikeEntity;
import com.example.socialnetwork.infrastructure.persistence.interactions.like.jpa.JpaLikeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LikeRepositoryJpaAdapter implements LikeRepository {
    private final JpaLikeRepository repository;

    public LikeRepositoryJpaAdapter(JpaLikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Like like) {
        repository.saveAndFlush(new LikeEntity(
                like.id().value(),
                like.postId().value(),
                like.authorId().value(),
                like.createdAt()
        ));
    }

    @Override
    public Optional<Like> findByAuthorAndPost(AuthorId authorId, PostId postId) {
        return repository.findByUserIdAndPostId(authorId.value(), postId.value())
                .map(likeEntity -> new Like(
                        LikeId.of(likeEntity.getId()),
                        AuthorId.of(likeEntity.getUserId()),
                        PostId.of(likeEntity.getPostId()),
                        likeEntity.getCreatedAt()
                ));
    }
}

