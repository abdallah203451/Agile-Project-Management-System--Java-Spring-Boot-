package com.project_management.application.dto.project;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInProjectDTO {
    private String email;
    private String role;
}
