package com.example.socialnetwork.infrastructure.persistence.post;

import com.example.socialnetwork.domain.post.AuthorId;
import com.example.socialnetwork.domain.post.Post;
import com.example.socialnetwork.domain.post.PostContent;
import com.example.socialnetwork.domain.post.PostId;
import com.example.socialnetwork.domain.post.ports.PostRepository;
import com.example.socialnetwork.infrastructure.persistence.post.adapters.PostRepositoryJpaAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostRepositoryJpaAdapter.class)
class PostRepositoryJpaAdapterTest {
    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

    @Autowired
    private PostRepository repository;

    @Test
    void save_and_find_post() {
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
