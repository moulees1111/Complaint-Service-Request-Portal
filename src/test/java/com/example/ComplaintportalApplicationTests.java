package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.serviceimpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserShouldEncodePassword() {

        UserRequestDto request = new UserRequestDto();
        request.setName("Test User");
        request.setEmail("testuser@example.com");
        request.setPassword("User@12345");

        // Adjust this if your DTO requires a role.
        request.setRole(com.example.entity.Role.USER);

        when(userRepository.existsByEmail(
                "testuser@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("User@12345"))
                .thenReturn("encoded-test-password");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        UserResponseDto response = userService.createUser(request);

        assertNotNull(response);

        verify(userRepository).existsByEmail(
                "testuser@example.com");

        verify(passwordEncoder).encode("User@12345");

        verify(userRepository).save(argThat(user ->
                user.getPassword().equals(
                        "encoded-test-password")));
    }
}