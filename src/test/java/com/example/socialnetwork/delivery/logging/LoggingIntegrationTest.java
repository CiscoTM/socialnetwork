package com.example.socialnetwork.delivery.logging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import support.IntegrationTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoggingIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldRegisterUserWithStructuredLogging() throws Exception {

        String json = """
                {
                  "id": "673a96d8-6f8b-41e4-b456-dfdf278d187f",
                  "email": "user21@example.com",
                  "displayName": "User 21"
                }
                """;

        mockMvc.perform(
                post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldFollowWithStructuredLogging() throws Exception {

        String json = """
                {
                  "follower": "2938e5a3-e6ad-4c10-8ec5-856948d0492d",
                  "followed": "7cf67aab-500f-4a7e-bf9b-a3729a988ae4"
                }
                """;

        mockMvc.perform(
                post("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void shouldLikeWithStructuredLogging() throws Exception {

        String json = """
            {
              "authorId": "c8692eaf-6d42-4013-8ebc-36ee5d39a3fa",
              "postId": "8fac050a-ec8d-49d3-ba29-39f5b0f32cb4"
            }
            """;

        mockMvc.perform(
                post("/api/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isConflict());
    }

}
