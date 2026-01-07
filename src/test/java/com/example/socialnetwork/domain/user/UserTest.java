package com.example.socialnetwork.domain.user;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserTest {

    @Test
    void creates_user_with_valid_values() {

        var id = UserId.generate();                              // Crea un UserId válido
        var email = UserEmail.of("test@example.com");           // Crea un UserEmail válido
        var user = User.create(id, email, "Francisco");   // Crea un User con valores correctos

        // Validaciones con AssertJ
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.email().value()).isEqualTo("test@example.com");
        assertThat(user.displayName()).isEqualTo("Francisco");
        assertThat(user.createdAt()).isNotNull();
    }

    @Test
    void rejects_invalid_email() {
        // Intenta crear un email inválido y espera excepción
        assertThatThrownBy(() -> UserEmail.of("bad-email"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejects_blank_display_name() {
        var id = UserId.generate();
        var email = UserEmail.of("fran@example.com");
        // Intenta crear un usuario con nombre vacío y espera excepción
        assertThatThrownBy(() -> User.create(id, email, ""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
