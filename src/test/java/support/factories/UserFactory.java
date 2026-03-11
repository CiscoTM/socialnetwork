package support.factories;

import com.example.socialnetwork.infrastructure.persistence.user.UserEntity;

import java.time.Instant;
import java.util.UUID;

public class UserFactory {

    public static UserEntity createUser(String email) {
        return new UserEntity(
                UUID.randomUUID(),
                email,
                "Test User",
                Instant.now(),
                email,
                "password",
                "USER"
        );
    }
}
