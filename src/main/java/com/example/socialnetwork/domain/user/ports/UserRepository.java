package com.example.socialnetwork.domain.user.ports;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(UserEmail email);

    // Guarda un usuario de dominio (sin password)
    void save(User user);

    // Guarda un usuario completo para autenticación
    void saveEntity(UserEntity entity);

    boolean existsByEmail(UserEmail email);
    void deleteAll();
}



