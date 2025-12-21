package support;

import com.example.socialnetwork.domain.user.User;
import com.example.socialnetwork.domain.user.UserEmail;
import com.example.socialnetwork.domain.user.UserId;
import com.example.socialnetwork.domain.user.ports.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> store = new ConcurrentHashMap<>();

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

