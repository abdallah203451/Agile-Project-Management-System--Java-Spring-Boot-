package com.project_management.presentation.controller;

import com.project_management.application.dto.task.TaskDto;
import com.project_management.application.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('PRODUCT_OWNER')")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<TaskDto>> getTasksBySprint(@PathVariable Long sprintId) {
        List<TaskDto> tasks = taskService.getTasksBySprint(sprintId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/assign")
    @PreAuthorize("hasRole('SCRUM_MASTER')")
    public ResponseEntity<TaskDto> assignTask(@PathVariable Long taskId, @RequestParam String assigneeEmail) {
        TaskDto updatedTask = taskService.assignTask(taskId, assigneeEmail);
        return ResponseEntity.ok(updatedTask);
    }
}
