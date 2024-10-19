package com.project_management.presentation.controller;

import com.project_management.application.dto.project.ProjectDto;
import com.project_management.application.dto.project.ProjectInfoDTO;
import com.project_management.application.dto.project.UserInProjectDTO;
import com.project_management.application.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/creator/{email}")
    public ResponseEntity<List<ProjectDto>> getProjectsByCreator(@PathVariable String email) {
        List<ProjectDto> projects = projectService.getProjectsByCreator(email);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<ProjectInfoDTO>> getProjectsByUser(@PathVariable String email) {
        List<ProjectInfoDTO> projects = projectService.getProjectsByUser(email);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}/users")
    public ResponseEntity<List<UserInProjectDTO>> getUsersAssignedToProject(@PathVariable Long projectId) {
        List<UserInProjectDTO> users = projectService.getUsersAssignedToProject(projectId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{projectId}/add-user")
    public ResponseEntity<ProjectInfoDTO> addUserToProject(@PathVariable Long projectId, @RequestParam String email) {
        ProjectInfoDTO updatedProject = projectService.addUserToProject(projectId, email);
        return ResponseEntity.ok(updatedProject);
    }
}
