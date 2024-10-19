package com.project_management.application.dto.user;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class TokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}
