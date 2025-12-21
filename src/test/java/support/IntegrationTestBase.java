package support;

import org.junit.jupiter.api.BeforeAll;

public abstract class IntegrationTestBase {
    @BeforeAll
    static void beforeAll(){
        PostgresTestContainer.getInstance().start();
    }
}
