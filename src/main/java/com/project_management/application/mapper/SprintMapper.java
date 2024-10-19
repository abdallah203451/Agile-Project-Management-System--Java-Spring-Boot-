package com.project_management.application.mapper;

import com.project_management.application.dto.sprint.SprintDto;
import com.project_management.domain.model.Sprint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SprintMapper {
    SprintDto toDto(Sprint sprint);
    Sprint toEntity(SprintDto sprintDto);
}
