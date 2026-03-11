package com.example.socialnetwork.delivery.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminSecurityIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void admin_endpoint_returns_403_for_user_role() throws Exception {
        mockMvc.perform(get("/admin/panel"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void admin_endpoint_returns_200_for_admin() throws Exception {
        mockMvc.perform(get("/admin/panel"))
                .andExpect(status().isOk());
    }
}

