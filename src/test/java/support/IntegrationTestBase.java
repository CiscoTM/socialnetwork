package support;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    private static final PostgresTestContainer POSTGRES = PostgresTestContainer.getInstance();

    static {
        POSTGRES.start(); // <-- se ejecuta antes de crear el ApplicationContext
    }
}

