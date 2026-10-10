package com.example.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.exception.DuplicateResourceException;
import static org.mockito.Mockito.never;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import com.example.exception.ResourceNotFoundException;

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
        request.setRole(Role.USER);

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

        UserResponseDto response =
                userService.createUser(request);

        assertNotNull(response);

        verify(passwordEncoder).encode("User@12345");

        verify(userRepository).save(argThat(user ->
                "encoded-test-password".equals(
                        user.getPassword())));
    }
    @Test
    void createUserShouldRejectDuplicateEmail() {

        UserRequestDto request = new UserRequestDto();
        request.setName("Test User");
        request.setEmail("testuser@example.com");
        request.setPassword("User@12345");
        request.setRole(Role.USER);

        when(userRepository.existsByEmail(
                "testuser@example.com"))
                .thenReturn(true);

        assertThrows(
            DuplicateResourceException.class,
            () -> userService.createUser(request)
        );

        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void getUserByIdShouldThrowExceptionWhenUserDoesNotExist() {

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> userService.getUserById(999L)
        );

        verify(userRepository).findById(999L);
    }
}