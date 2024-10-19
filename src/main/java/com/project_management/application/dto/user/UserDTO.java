package com.project_management.application.dto.user;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role; // Enum Role
}
