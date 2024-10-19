package com.project_management.project_management.service;

import com.project_management.application.Enum.Role;
import com.project_management.application.dto.user.LoginRequestDTO;
import com.project_management.application.dto.user.RegisterRequestDTO;
import com.project_management.application.dto.user.TokenResponseDTO;
import com.project_management.application.dto.user.UserDTO;
import com.project_management.application.mapper.UserMapper;
import com.project_management.application.repository.UserRepository;
import com.project_management.domain.model.User;
import com.project_management.infrastructure.security.JwtTokenProvider;
import com.project_management.infrastructure.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserMapper userMapper;

    @Test
    public void testRegisterUser() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("testUser")
                .email("test@test.com")
                .password("password")
                .role(Role.PROJECT_MANAGER)
                .build();

        User user = User.builder()
                .username("testUser")
                .email("test@test.com")
                .password("encodedPassword")
                .role(Role.PROJECT_MANAGER)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("testUser")
                .email("test@test.com")
                .role("USER")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.register(request);

        assertThat(result.getUsername()).isEqualTo("testUser");
        assertThat(result.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    public void testLoginUser() {
        LoginRequestDTO loginRequest = LoginRequestDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();

        User user = User.builder()
                .username("testUser")
                .email("test@test.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.PROJECT_MANAGER)
                .build();

        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        Mockito.when(jwtTokenProvider.createToken(Mockito.anyString(), Mockito.anyString())).thenReturn("accessToken");
        Mockito.when(jwtTokenProvider.createRefreshToken(Mockito.anyString(), Mockito.anyString())).thenReturn("refreshToken");

        TokenResponseDTO response = userService.login(loginRequest);

        assertThat(response.getAccessToken()).isEqualTo("accessToken");
        assertThat(response.getRefreshToken()).isEqualTo("refreshToken");
    }
}
