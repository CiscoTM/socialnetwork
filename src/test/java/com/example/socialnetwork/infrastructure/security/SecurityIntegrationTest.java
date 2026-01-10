package com.example.socialnetwork.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {},
        useDefaultFilters = false,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = com.example.socialnetwork.infrastructure.security.SecurityConfig.class
        )
)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SecurityIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void protected_endpoint_returns_401_without_credentials() throws Exception {
        mockMvc.perform(get("/api/protected"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protected_endpoint_returns_404_with_valid_credentials() throws Exception {
        String basicAuth = "Basic " +
                java.util.Base64.getEncoder()
                        .encodeToString("admin:password".getBytes());

        mockMvc.perform(
                get("/api/protected")
                        .header("Authorization", basicAuth)
        ).andExpect(status().isNotFound());
    }
}
