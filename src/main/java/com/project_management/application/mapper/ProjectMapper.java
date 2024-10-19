package com.project_management.application.mapper;

import com.project_management.application.dto.project.ProjectDto;
import com.project_management.application.dto.project.ProjectInfoDTO;
import com.project_management.domain.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDto toDto(Project project);
    ProjectInfoDTO toInfoDTO(Project project);
    Project toEntity(ProjectDto projectDto);
}
