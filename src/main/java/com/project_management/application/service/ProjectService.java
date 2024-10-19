package com.project_management.application.service;

import com.project_management.application.dto.project.ProjectDto;
import com.project_management.application.dto.project.ProjectInfoDTO;
import com.project_management.application.dto.project.UserInProjectDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);

    List<ProjectDto> getProjectsByCreator(String creatorEmail);

    List<ProjectInfoDTO> getProjectsByUser(String userEmail);

    List<UserInProjectDTO> getUsersAssignedToProject(Long projectId);
    ProjectInfoDTO addUserToProject(Long projectId, String userEmail);
}
