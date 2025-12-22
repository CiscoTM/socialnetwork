package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidPostIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PostIdTest {
    @Test
    void shouldCreateValidPostId(){
        PostId id = PostId.generate();
        assertNotNull(id.value());
    }
    @Test
    void shouldThrowExceptionWhenPostIdIsNull(){
        assertThrows(InvalidPostIdException.class, () -> new PostId(null));
    }
    @Test
    void shouldCreatePostIdFromUUID(){
        UUID uuid = UUID.randomUUID();
        PostId id = PostId.of(uuid);
        assertEquals(uuid, id.value());
    }
}
