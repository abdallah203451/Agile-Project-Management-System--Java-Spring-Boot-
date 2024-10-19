package com.project_management.application.mapper;

import com.project_management.application.dto.task.TaskDto;
import com.project_management.domain.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);
}
