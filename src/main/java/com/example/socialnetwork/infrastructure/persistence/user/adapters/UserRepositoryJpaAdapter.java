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

    @Override public Optional<User> findById(UserId id) { return jpa.findById(id.value()).map(this::toDomain); }
    @Override public Optional<User> findByEmail(UserEmail email) { return jpa.findByEmail(email.value()).map(this::toDomain);}
    @Override public void save(User user) {
        try {
            jpa.save(toEntity(user));
        } catch (Exception ex) {
            if (ex.getMessage().contains("users_email_key") || ex.getMessage().contains("unique")) {
                throw new UserAlreadyExistsException(user.email().value());
            }
            throw ex;
        }
    }
    @Override public boolean existsByEmail(UserEmail email) { return jpa.existsByEmail(email.value()); }

    private User toDomain(UserEntity e) {
        return User.restore(
                UserId.of(e.getId()),
                UserEmail.of(e.getEmail()),
                e.getDisplayName(),
                e.getCreatedAt()
        );
    }
    private UserEntity toEntity(User u) {
        return new UserEntity(
                u.id().value(),
                u.email().value(),
                u.displayName(),
                u.createdAt()
        );
    }
}
