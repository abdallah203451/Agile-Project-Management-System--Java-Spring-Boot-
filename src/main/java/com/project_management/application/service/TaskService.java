package com.project_management.application.service;

import com.project_management.application.dto.task.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getTasksBySprint(Long sprintId);

    TaskDto assignTask(Long taskId, String assigneeEmail);
}
