package com.example.socialnetwork.domain.post;

import com.example.socialnetwork.domain.post.exception.InvalidPostCreationDateException;

import java.time.Instant;

public record Post(
        PostId id,
        AuthorId authorId,
        PostContent content,
        Instant createdAt
) {

    // Constructor privado: solo se usa desde create() y restore()
    public Post {
        if (createdAt == null) {
            throw new InvalidPostCreationDateException("Creation date cannot be null");
        }
    }

    /**
     * Crea un nuevo Post aplicando reglas de negocio:
     * - Genera un PostId automáticamente
     * - Establece createdAt = Instant.now()
     * - Los Value Objects validan sus propios invariantes
     */
    public static Post create(AuthorId authorId, PostContent content) {
        return new Post(
                PostId.generate(),
                AuthorId.of(authorId.value())
                ,
                content,
                Instant.now()
        );
    }

    /**
     * Restaura un Post desde persistencia:
     * - No aplica reglas de negocio
     * - Usa los valores tal cual vienen de la base de datos
     */
    public static Post restore(PostId id, AuthorId authorId, PostContent content, Instant createdAt) {
        return new Post(id, authorId, content, createdAt);
    }
}

