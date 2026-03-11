package support;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

@Testcontainers
public abstract class PostgresTestContainer {

    @Container
    protected static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("socialnetwork")
                    .withUsername("test")
                    .withPassword("test")
                    .withEnv("POSTGRES_HOST_AUTH_METHOD", "trust") // Compatibilidad Podman
                    .withReuse(true)
                    .waitingFor(
                            Wait.forListeningPort()
                                    .withStartupTimeout(Duration.ofSeconds(60))
                    );

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES::getDriverClassName);

        registry.add("spring.liquibase.enabled", () -> true);
        registry.add("spring.liquibase.url", POSTGRES::getJdbcUrl);
        registry.add("spring.liquibase.user", POSTGRES::getUsername);
        registry.add("spring.liquibase.password", POSTGRES::getPassword);

        registry.add("spring.datasource.hikari.maximum-pool-size", () -> 5);
        registry.add("spring.datasource.hikari.minimum-idle", () -> 1);
        registry.add("spring.datasource.hikari.idle-timeout", () -> 10000);
        registry.add("spring.datasource.hikari.max-lifetime", () -> 30000);
        registry.add("spring.datasource.hikari.connection-timeout", () -> 60000);
        registry.add("spring.datasource.hikari.validation-timeout", () -> 10000);

        registry.add("spring.security.filter.dispatcher-types", () -> "ASYNC,ERROR,REQUEST");
        registry.add("spring.security.csrf.enabled", () -> false);
    }
}
