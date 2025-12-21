package support;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String IMAGE = "postgres:15";
    private static PostgresTestContainer container;

    private PostgresTestContainer(){
        super(IMAGE);
    }
    public static PostgresTestContainer getInstance(){
        if(container == null){
            container = new PostgresTestContainer()
                    .withDatabaseName("testdb")
                    .withUsername("admin")
                    .withPassword("admin");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());

        System.setProperty("spring.liquibase.url", container.getJdbcUrl());
        System.setProperty("spring.liquibase.user", container.getUsername());
        System.setProperty("spring.liquibase.password", container.getPassword());

    }

    @Override
    public void stop() {
        // No-op: Testcontainers se encarga de la limpieza
    }
}
