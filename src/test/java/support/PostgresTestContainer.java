package support;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    private static final String IMAGE = "postgres:15";
    private static PostgresTestContainer container;

    private PostgresTestContainer() {
        super(IMAGE);
    }

    public static PostgresTestContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer()
                    .withDatabaseName("testdb")
                    .withUsername("admin")
                    .withPassword("admin");
        }
        return container;
    }

    @Override
    public void stop() {
        // Testcontainers gestiona la limpieza
    }
}
