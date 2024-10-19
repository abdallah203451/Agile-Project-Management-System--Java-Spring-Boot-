package com.project_management.project_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_management.application.Enum.Role;
import com.project_management.application.dto.project.ProjectDto;
import com.project_management.application.repository.ProjectRepository;
import com.project_management.application.repository.UserRepository;
import com.project_management.domain.model.Project;
import com.project_management.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "PROJECT_MANAGER")
    public void testCreateProject() throws Exception {
        User creator = User.builder().email("creator@test.com").role(Role.PROJECT_MANAGER).build();
        userRepository.save(creator);

        ProjectDto projectDto = ProjectDto.builder()
                .name("Test Project")
                .description("Test Description")
                .creatorEmail("creator@test.com")
                .build();

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    @WithMockUser
    public void testGetProjectsByUser() throws Exception {
        User user = User.builder().email("user@test.com").role(Role.PROJECT_MANAGER).build();
        userRepository.save(user);

        Project project = Project.builder().name("Project 1").build();
        project.getUsers().add(user);
        projectRepository.save(project);

        mockMvc.perform(get("/api/projects/user/{email}", "user@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Project 1"));
    }
}
