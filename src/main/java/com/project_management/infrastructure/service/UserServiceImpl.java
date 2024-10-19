package com.project_management.infrastructure.service;

import com.project_management.application.Enum.Role;
import com.project_management.application.dto.user.*;
import com.project_management.application.mapper.UserMapper;
import com.project_management.application.repository.UserRepository;
import com.project_management.application.service.UserService;
import com.project_management.domain.model.User;
import com.project_management.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public UserDTO register(RegisterRequestDTO registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf(registerRequest.getRole().name()))
                .build();

        userRepository.save(user);
        UserDTO userDto = userMapper.toDTO(user);
        return userDto;
    }

    @Override
    public TokenResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String accessToken = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getRole().name());

            return new TokenResponseDTO(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    @Override
    public String refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
        return jwtTokenProvider.refreshToken(refreshTokenRequest.getRefreshToken());
    }
}