package com.project_management.presentation.controller;

import com.project_management.application.dto.sprint.SprintDto;
import com.project_management.application.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {
    private final SprintService sprintService;

    @PostMapping
    @PreAuthorize("hasRole('SCRUM_MASTER')")
    public ResponseEntity<SprintDto> createSprint(@RequestBody SprintDto sprintDto) {
        SprintDto createdSprint = sprintService.createSprint(sprintDto);
        return new ResponseEntity<>(createdSprint, HttpStatus.CREATED);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<SprintDto>> getSprintsByProject(@PathVariable Long projectId) {
        List<SprintDto> sprints = sprintService.getSprintsByProject(projectId);
        return ResponseEntity.ok(sprints);
    }
}
