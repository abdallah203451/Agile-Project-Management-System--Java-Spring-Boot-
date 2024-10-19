package com.project_management.application.service;

import com.project_management.application.dto.sprint.SprintDto;

import java.util.List;

public interface SprintService {
    SprintDto createSprint(SprintDto sprintDto);

    List<SprintDto> getSprintsByProject(Long projectId);
}
