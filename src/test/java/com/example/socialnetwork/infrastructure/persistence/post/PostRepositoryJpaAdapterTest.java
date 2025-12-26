package com.example.socialnetwork.infrastructure.persistence.post;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import com.example.socialnetwork.infrastructure.persistence.post.adapters.PostRepositoryJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import support.IntegrationTestBase;
import support.PostgresTestContainer;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(PostRepositoryJpaAdapter.class)
class PostRepositoryJpaAdapterTest {

    private static final PostgresTestContainer POSTGRES = PostgresTestContainer.getInstance();

    static {
        POSTGRES.start();
    }

    @Autowired private PostRepository repository;

    @Test
    @Sql(statements = "DELETE FROM posts")
    void save_and_find_post() {
        System.out.println("Repository class = " + repository.getClass());
        PostId id = PostId.generate();
        AuthorId authorId = AuthorId.of(UUID.randomUUID());

        Post post = Post.restore(
                id,
                authorId,
                PostContent.of("Hello world"),
                Instant.now()
        );
        repository.save(post);

        Post loaded = repository.findById(id).orElseThrow();

        assertThat(loaded.id().value()).isEqualTo(id.value());
        assertThat(loaded.authorId().value()).isEqualTo(authorId.value());
        assertThat(loaded.content().value()).isEqualTo("Hello world");
    }
}


