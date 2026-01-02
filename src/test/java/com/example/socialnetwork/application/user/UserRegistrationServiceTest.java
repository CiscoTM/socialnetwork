package com.example.socialnetwork.application.user;

import com.example.socialnetwork.application.user.service.UserRegistrationService;
import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.exceptions.UserAlreadyExistsException;
import com.example.socialnetwork.domain.user.ports.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserRegistrationServiceTest {

    private static class InMemoryUserRepository implements UserRepository {

        private final Map<String, User> store = new HashMap<>();

        @Override
        public Optional<User> findById(UserId id) {
            return Optional.ofNullable(store.get(id.value()));
        }

        @Override
        public Optional<User> findByEmail(UserEmail email) {
            return store.values().stream()
                    .filter(u -> u.email().equals(email))
                    .findFirst();
        }

        @Override
        public void save(User user) {
            store.put(user.id().value(), user);
        }

        @Override
        public boolean existsByEmail(UserEmail email) {
            return findByEmail(email).isPresent();
        }
    }

    private UserRegistrationService createService(UserRepository userRepo) {
        return new UserRegistrationService(userRepo);
    }

    @Test
    void registers_user_with_unique_email() {
        UserRepository userRepo = new InMemoryUserRepository();
        UserRegistrationService service = createService(userRepo);

        UserId id = UserId.of("u-1");
        UserEmail email = UserEmail.of("new@example.com");

        User user = service.register(
                id.value(),
                email.value(),
                "Francisco"
        );

        assertThat(user.id()).isEqualTo(id);
        assertThat(user.email()).isEqualTo(email);
        assertThat(user.displayName()).isEqualTo("Francisco");
        assertThat(userRepo.findById(id)).contains(user);
    }

    @Test
    void rejects_duplicate_email() {
        UserRepository userRepo = new InMemoryUserRepository();
        UserRegistrationService service = createService(userRepo);

        UserId id1 = UserId.of("u-1");
        UserEmail email = UserEmail.of("dup@example.com");

        service.register(id1.value(), email.value(), "Fran");

        UserId id2 = UserId.of("u-2");

        assertThatThrownBy(() ->
                service.register(id2.value(), email.value(), "Fran")
        )
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining(String.format(UserAlreadyExistsException.MESSAGE, email.value()));
    }
}
