package com.project_management.project_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_management.application.Enum.Role;
import com.project_management.application.dto.user.LoginRequestDTO;
import com.project_management.application.dto.user.RegisterRequestDTO;
import com.project_management.application.repository.UserRepository;
import com.project_management.domain.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser() throws Exception {
        RegisterRequestDTO registerRequest = RegisterRequestDTO.builder()
                .username("testUser")
                .email("test@test.com")
                .password("password")
                .role(Role.PROJECT_MANAGER)
                .build();

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testLoginUser() throws Exception {
        User user = User.builder()
                .username("testUser")
                .email("test@test.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .role(Role.PROJECT_MANAGER)
                .build();

        userRepository.save(user);

        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }
}
