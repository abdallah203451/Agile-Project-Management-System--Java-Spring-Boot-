package com.project_management.application.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequestDTO {
    private String refreshToken;
}
