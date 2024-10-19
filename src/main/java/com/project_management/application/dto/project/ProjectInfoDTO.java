package com.project_management.application.dto.project;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInfoDTO {
    private Long id;
    private String name;
    private List<UserInProjectDTO> users;
}
