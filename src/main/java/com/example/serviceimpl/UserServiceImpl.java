package com.example.serviceimpl;

import com.example.dto.UserRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.dto.UserResponseDto;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    

    @Override
    public UserResponseDto createUser(UserRequestDto request) {
    	

        log.info("Creating user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        User user = UserMapper.toEntity(request);
    	user.setPassword(
    	    passwordEncoder.encode(request.getPassword())
    	);
        User savedUser = userRepository.save(user);

        log.info(
                "User created successfully with ID: {}",
                savedUser.getId()
        );

        return UserMapper.toResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {

        log.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return UserMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {

        log.info("Fetching all users");

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto updateUser(
            Long id,
            UserRequestDto request) {

        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {
            user.setPassword(
                passwordEncoder.encode(request.getPassword())
            );
        }
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        log.info(
                "User updated successfully with ID: {}",
                id
        );

        return UserMapper.toResponseDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "User not found"
            );
        }

        userRepository.deleteById(id);

        log.info(
                "User deleted successfully with ID: {}",
                id
        );
    }
}