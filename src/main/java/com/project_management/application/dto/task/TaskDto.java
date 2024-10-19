package com.project_management.application.dto.task;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long sprintId;
    private String assigneeEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
