
package com.example.userservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice.dto.request.UserRequestDto;
import com.example.userservice.dto.request.UserUpdateRequestDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.EmailAlreadyExistsException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Register a new user
    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto request) {

        log.info("Registering user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered");
        }

        User user = userMapper.toEntity(request);

        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with ID: {}",
                savedUser.getId());

        return userMapper.toResponseDto(savedUser);
    }

    // Get user by ID
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {

        log.info("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + id));

        return userMapper.toResponseDto(user);
    }

    // Get user by email
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {

        log.info("Fetching user by email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with the supplied email"));

        return userMapper.toResponseDto(user);
    }

    // Update user profile
    @Override
    @Transactional
    public UserResponseDto updateUser(
            Long id,
            UserUpdateRequestDto request) {

        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + id));

        boolean emailUsedByAnotherUser =
                userRepository.existsByEmailAndIdNot(
                        request.getEmail(), id);

        if (emailUsedByAnotherUser) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered by another user");
        }

        userMapper.updateEntity(request, user);

        User updatedUser = userRepository.save(user);

        log.info("User updated successfully with ID: {}", id);

        return userMapper.toResponseDto(updatedUser);
    }

    // Delete user
    @Override
    @Transactional
    public void deleteUser(Long id) {

        log.info("Deleting user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + id));

        userRepository.delete(user);

        log.info("User deleted successfully with ID: {}", id);
    }
}
