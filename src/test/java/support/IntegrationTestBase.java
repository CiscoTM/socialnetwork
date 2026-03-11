package support;

import com.example.socialnetwork.infrastructure.security.TestJwtTokenProvider;
import com.example.socialnetwork.infrastructure.security.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
                "spring.security.enabled=true",
                "spring.main.allow-bean-definition-overriding=true"
        }
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class, TestJwtTokenProvider.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class IntegrationTestBase extends PostgresTestContainer {
}
