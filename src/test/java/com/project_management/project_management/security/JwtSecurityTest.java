package com.project_management.project_management.security;

import com.project_management.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @WithMockUser(roles = "PROJECT_MANAGER")
    public void testAccessSecuredEndpoint_WithJwtToken() throws Exception {
        String token = jwtTokenProvider.createToken("user@test.com", "ROLE_PROJECT_MANAGER");

        mockMvc.perform(post("/api/projects")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessSecuredEndpoint_WithoutJwtToken() throws Exception {
        mockMvc.perform(post("/api/projects"))
                .andExpect(status().isForbidden());
    }
}
