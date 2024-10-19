package com.project_management.application.mapper;

import com.project_management.application.dto.user.UserDTO;
import com.project_management.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
