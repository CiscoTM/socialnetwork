package com.example.socialnetwork.infrastructure.persistence.post.adapters;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import com.example.socialnetwork.infrastructure.persistence.post.PostEntity;
import com.example.socialnetwork.infrastructure.persistence.post.jpa.JpaPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class PostRepositoryJpaAdapter implements PostRepository {

    private final JpaPostRepository jpa;

    @Autowired
    public PostRepositoryJpaAdapter(JpaPostRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Post> findById(PostId id) {
        return jpa.findById(id.value()).map(this::toDomain);
    }
    @Override
    public void save(Post post) {
        jpa.save(toEntity(post));
    }
    @Override
    public void deleteById(PostId id) {
        jpa.deleteById(id.value());
    }

    private Post toDomain(PostEntity entity){
        return Post.restore(
                PostId.of(entity.getId()),
                AuthorId.of(entity.getAuthorId()),
                PostContent.of(entity.getContent()),
                entity.getCreatedAt()
        );
    }

    private PostEntity toEntity(Post post){
        return new PostEntity(
                post.id().value(),
                post.authorId().value(),
                post.content().value(),
                post.createdAt()
        );
    }
}
