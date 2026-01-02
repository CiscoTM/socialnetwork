package com.example.socialnetwork.infrastructure.persistence.interactions.comment.adapters;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.CommentContent;
import com.example.socialnetwork.domain.interactions.comment.CommentId;
import com.example.socialnetwork.domain.interactions.comment.ports.CommentRepository;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.infrastructure.persistence.interactions.comment.CommentEntity;
import com.example.socialnetwork.infrastructure.persistence.interactions.comment.jpa.JpaCommentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentRepositoryJpaAdapter implements CommentRepository {

    private final JpaCommentRepository repository;

    public CommentRepositoryJpaAdapter(JpaCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Comment comment) {
        CommentEntity entity = new CommentEntity(
                comment.id().value(),
                comment.postId().value(),
                comment.authorId().value(),
                comment.content().value(),
                comment.createdAt()
        );

        // IMPORTANTE: forzamos el flush para que la violación de FK
        // (post_id o author_id inexistente) se produzca dentro de este método
        repository.saveAndFlush(entity);
    }

    @Override
    public Optional<Comment> findById(CommentId id) {
        return repository.findById(id.value())
                .map(entity -> new Comment(
                        CommentId.of(entity.getId()),
                        AuthorId.of(entity.getAuthorId()),
                        PostId.of(entity.getPostId()),
                        CommentContent.of(entity.getContent()),
                        entity.getCreatedAt()
                ));
    }

    @Override
    public List<Comment> findByPostId(PostId postId) {
        return repository.findByPostId(postId.value())
                .stream()
                .map(entity -> new Comment(
                        CommentId.of(entity.getId()),
                        AuthorId.of(entity.getAuthorId()),
                        PostId.of(entity.getPostId()),
                        CommentContent.of(entity.getContent()),
                        entity.getCreatedAt()
                ))
                .toList();
    }
}
