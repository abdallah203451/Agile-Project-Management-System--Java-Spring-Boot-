package com.project_management.infrastructure.service;

import com.project_management.application.Enum.Role;
import com.project_management.application.Enum.Status;
import com.project_management.application.dto.task.TaskDto;
import com.project_management.application.mapper.TaskMapper;
import com.project_management.application.repository.SprintRepository;
import com.project_management.application.repository.TaskRepository;
import com.project_management.application.repository.UserRepository;
import com.project_management.application.service.TaskService;
import com.project_management.domain.model.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;
    @Autowired
    private final TaskMapper taskMapper;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Sprint sprint = sprintRepository.findById(taskDto.getSprintId())
                .orElseThrow(() -> new EntityNotFoundException("Sprint not found"));

        Task task = taskMapper.toEntity(taskDto);
        task.setSprint(sprint);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(Status.TO_DO); // default status when created

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public List<TaskDto> getTasksBySprint(Long sprintId) {
        List<Task> tasks = taskRepository.findBySprintId(sprintId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TaskDto assignTask(Long taskId, String assigneeEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        User assignee = userRepository.findByEmail(assigneeEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!assignee.getRole().equals(Role.DEVELOPER) && !assignee.getRole().equals(Role.TESTER)) {
            throw new IllegalArgumentException("Only developers or testers can be assigned to tasks.");
        }

        task.setAssignee(assignee);
        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }
}