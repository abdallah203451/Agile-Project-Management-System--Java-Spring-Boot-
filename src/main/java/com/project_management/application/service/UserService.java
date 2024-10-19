package com.project_management.application.service;

import com.project_management.application.dto.user.*;

public interface UserService {
    UserDTO register(RegisterRequestDTO registerRequest);
    TokenResponseDTO login(LoginRequestDTO loginRequest);
    String refreshToken(RefreshTokenRequestDTO refreshTokenRequest);
}
