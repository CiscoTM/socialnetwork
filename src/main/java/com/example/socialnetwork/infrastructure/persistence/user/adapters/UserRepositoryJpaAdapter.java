package com.example.socialnetwork.infrastructure.persistence.user.adapters;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.exceptions.UserAlreadyExistsException;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;
import com.example.socialnetwork.infrastructure.persistence.user.jpa.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJpaAdapter implements UserRepository {

    private final JpaUserRepository jpa;

    public UserRepositoryJpaAdapter(JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpa.findById(id.value()).map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(UserEmail email) {
        return jpa.findByEmail(email.value()).map(this::toDomain);
    }

    @Override
    public void save(User user) {
        // Persistencia de dominio (sin password ni role)
        UserEntity entity = new UserEntity(
                user.id().value(),
                user.email().value(),
                user.displayName(),
                user.createdAt(),
                user.email().value(),   // username = email
                "",                     // password vacío
                "USER"                  // role por defecto
        );
        jpa.save(entity);
    }

    @Override
    public void saveEntity(UserEntity entity) {
        jpa.save(entity);
    }

    @Override
    public boolean existsByEmail(UserEmail email) {
        return jpa.existsByEmail(email.value());
    }

    @Override
    public void deleteAll() {
        jpa.deleteAll();
    }

    private User toDomain(UserEntity e) {
        return User.restore(
                UserId.of(e.getId()),
                UserEmail.of(e.getEmail()),
                e.getDisplayName(),
                e.getCreatedAt()
        );
    }
}

