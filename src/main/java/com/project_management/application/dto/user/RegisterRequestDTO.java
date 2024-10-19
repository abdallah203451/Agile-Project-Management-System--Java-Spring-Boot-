package com.project_management.application.dto.user;


import com.project_management.application.Enum.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private Role role;
}
