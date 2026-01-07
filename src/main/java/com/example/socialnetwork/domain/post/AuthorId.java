package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidAuthorIdException;
import java.util.UUID;

public record AuthorId(UUID value) {

    public AuthorId {
        if (value == null) {
            throw new InvalidAuthorIdException("AuthorId cannot be null");
        }
    }

    public static AuthorId of(UUID value) {
        return new AuthorId(value);
    }

    public static AuthorId fromString(String value) {
        return new AuthorId(UUID.fromString(value));
    }
}
