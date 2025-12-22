package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidAuthorIdException;
import com.example.socialnetwork.domain.post.exception.NullAuthorIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorIdTest {

    @Test
    void shouldCreateValidAuthorId(){
        AuthorId id = AuthorId.of(UUID.randomUUID());
        assertNotNull(id.value());

    }
    @Test
    void shouldThrowExceptionWhenAuthorIdIsNull(){
        assertThrows(InvalidAuthorIdException.class, () -> AuthorId.of(null));
    }
}
