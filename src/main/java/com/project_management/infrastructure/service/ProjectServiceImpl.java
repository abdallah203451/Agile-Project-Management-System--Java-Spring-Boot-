package com.project_management.infrastructure.service;

import com.project_management.application.dto.project.ProjectDto;
import com.project_management.application.dto.project.ProjectInfoDTO;
import com.project_management.application.dto.project.UserInProjectDTO;
import com.project_management.application.mapper.ProjectMapper;
import com.project_management.application.repository.ProjectRepository;
import com.project_management.application.repository.UserRepository;
import com.project_management.application.service.ProjectService;
import com.project_management.domain.model.Project;
import com.project_management.domain.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Autowired
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        User creator = userRepository.findByEmail(projectDto.getCreatorEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Project project = projectMapper.toEntity(projectDto);
        project.setCreator(creator);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    public List<ProjectDto> getProjectsByCreator(String creatorEmail) {
        List<Project> projects = projectRepository.findByCreatorEmail(creatorEmail);
        return projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectInfoDTO> getProjectsByUser(String userEmail) {
        List<Project> projects = projectRepository.findProjectsByUserEmail(userEmail);
        return projects.stream()
                .map(projectMapper::toInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInProjectDTO> getUsersAssignedToProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        return project.getUsers().stream()
                .map(user -> new UserInProjectDTO(user.getEmail(), user.getRole().name())) // Assuming User has a Role enum
                .collect(Collectors.toList());
    }

    @Override
    public ProjectInfoDTO addUserToProject(Long projectId, String userEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        project.getUsers().add(user); // Add user to the project

        projectRepository.save(project); // Save the updated project

        return projectMapper.toInfoDTO(project);
    }

}