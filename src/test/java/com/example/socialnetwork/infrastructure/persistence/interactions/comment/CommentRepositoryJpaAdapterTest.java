package com.example.socialnetwork.infrastructure.persistence.interactions.comment;

import com.example.socialnetwork.domain.interactions.comment.Comment;
import com.example.socialnetwork.domain.interactions.comment.CommentContent;
import com.example.socialnetwork.domain.interactions.comment.CommentId;
import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.infrastructure.persistence.interactions.comment.adapters.CommentRepositoryJpaAdapter;
import com.example.socialnetwork.infrastructure.persistence.interactions.comment.jpa.JpaCommentRepository;
import com.example.socialnetwork.infrastructure.persistence.post.PostEntity;
import com.example.socialnetwork.infrastructure.persistence.post.jpa.JpaPostRepository;
import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import com.example.socialnetwork.infrastructure.persistence.user.jpa.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import support.JpaTestBase;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@Import(CommentRepositoryJpaAdapter.class)
class CommentRepositoryJpaAdapterTest extends JpaTestBase {

    @Autowired private CommentRepositoryJpaAdapter repository;
    @Autowired private JpaCommentRepository jpaRepository;
    @Autowired private JpaPostRepository postRepository;
    @Autowired private JpaUserRepository userRepository;

    @Test
    void save_and_load_comment_successfully() {
        UUID postIdValue = UUID.randomUUID();
        UUID authorIdValue = UUID.randomUUID();

        postRepository.save(new PostEntity(
                postIdValue,
                authorIdValue,
                "Test content",
                Instant.now()
        ));

        userRepository.save(new UserEntity(
                authorIdValue.toString(),
                "user@test.com",
                "User Name",
                Instant.now()
        ));

        CommentId id = CommentId.generate();
        Comment comment = Comment.create(
                id,
                AuthorId.of(authorIdValue),
                PostId.of(postIdValue),
                CommentContent.of("Great post!")
        );

        repository.save(comment);

        var entity = jpaRepository.findById(id.value()).orElseThrow();

        assertThat(entity.getId()).isEqualTo(id.value());
        assertThat(entity.getPostId()).isEqualTo(postIdValue);
        assertThat(entity.getAuthorId()).isEqualTo(authorIdValue);
        assertThat(entity.getContent()).isEqualTo("Great post!");
    }

    @Test
    void saving_comment_with_nonexistent_post_should_fail() {
        Comment comment = Comment.create(
                CommentId.generate(),
                AuthorId.of(UUID.randomUUID()),
                PostId.of(UUID.randomUUID()),
                CommentContent.of("Invalid comment")
        );

        assertThatThrownBy(() -> repository.save(comment))
                .isInstanceOf(Exception.class);
    }
}
