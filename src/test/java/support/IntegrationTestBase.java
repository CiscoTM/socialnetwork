package support;

import com.example.socialnetwork.domain.post.ports.PostRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
public abstract class IntegrationTestBase {
    private static final PostgresTestContainer POSTGRES = PostgresTestContainer.getInstance();

    static {
        POSTGRES.start();
    }
    // Mock global para todos los tests de integración
    @MockitoBean
    protected PostRepository postRepository;
}
