package com.project_management.project_management.service;

import com.project_management.application.dto.project.ProjectDto;
import com.project_management.application.mapper.ProjectMapper;
import com.project_management.application.repository.ProjectRepository;
import com.project_management.application.repository.UserRepository;
import com.project_management.domain.model.Project;
import com.project_management.domain.model.User;
import com.project_management.infrastructure.service.ProjectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Test
    public void testCreateProject() {
        ProjectDto projectDto = ProjectDto.builder()
                .name("Test Project")
                .description("Test Description")
                .creatorEmail("creator@test.com")
                .build();

        User creator = User.builder().id(1L).email("creator@test.com").build();

        Project project = Project.builder().name("Test Project").description("Test Description").creator(creator).build();

        Mockito.when(userRepository.findByEmail("creator@test.com")).thenReturn(Optional.of(creator));
        Mockito.when(projectMapper.toEntity(projectDto)).thenReturn(project);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        Mockito.when(projectMapper.toDto(project)).thenReturn(projectDto);

        ProjectDto result = projectService.createProject(projectDto);

        assertThat(result.getName()).isEqualTo("Test Project");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getCreatorEmail()).isEqualTo("creator@test.com");
    }

    @Test
    public void testGetProjectsByCreator() {
        String creatorEmail = "creator@test.com";

        User creator = User.builder().id(1L).email(creatorEmail).build();

        Project project1 = Project.builder().name("Project 1").creator(creator).build();
        Project project2 = Project.builder().name("Project 2").creator(creator).build();

        List<Project> projects = Arrays.asList(project1, project2);

        Mockito.when(projectRepository.findByCreatorEmail(creatorEmail)).thenReturn(projects);
        Mockito.when(projectMapper.toDto(Mockito.any(Project.class)))
                .thenAnswer(invocation -> {
                    Project project = invocation.getArgument(0);
                    return ProjectDto.builder().name(project.getName()).build();
                });

        List<ProjectDto> result = projectService.getProjectsByCreator(creatorEmail);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Project 1");
        assertThat(result.get(1).getName()).isEqualTo("Project 2");
    }
}
