package com.project_management.application.dto.sprint;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long projectId;
    private String email;
}
