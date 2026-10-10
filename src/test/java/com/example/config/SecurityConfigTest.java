
package com.example.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void unauthenticatedUserCannotAccessUsersEndpoint()
            throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void normalUserCannotAccessUsersEndpoint() throws Exception {
        mockMvc.perform(get("/api/users")
                .with(user("testuser").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanAccessUsersEndpoint() throws Exception {
        mockMvc.perform(get("/api/users")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }
}