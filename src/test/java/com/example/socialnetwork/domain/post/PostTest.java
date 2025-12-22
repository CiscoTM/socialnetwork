package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


public class PostTest {
    @Test
    void shouldCreateValidPost(){
        Post post = Post.create( AuthorId.of(UUID.randomUUID()),PostContent.of("Hello"));
        assertNotNull(post);
    }
    @Test
    void shouldThrowExceptionWhenPostIdIsNull(){
        assertThrows(InvalidPostIdException.class, () -> new Post(
            PostId.of(null),
            AuthorId.of(UUID.randomUUID()),
            PostContent.of("Hello"),
            Instant.now()
        ) );
    }
    @Test
    void shouldThrowExceptionWhenAuthorIdIsNull() {
        assertThrows(InvalidAuthorIdException.class, () -> Post.create(
                AuthorId.of(null),
                PostContent.of("Hello")
        ));
    }
    @Test
    void shouldThrowExceptionWhenContentIsNull() {
        assertThrows(NullPostContentException.class, () -> Post.create(AuthorId.of(UUID.randomUUID()), PostContent.of(null)) );
    }
    @Test
    void shouldThrowExceptionWhenCreatedAtIsNull() {
        assertThrows(InvalidPostCreationDateException.class, () -> new Post(PostId.generate(), AuthorId.of(UUID.randomUUID()), PostContent.of("Hello"), null) );
    }
}
