
package com.example.userservice.service;

import com.example.userservice.dto.request.UserRequestDto;
import com.example.userservice.dto.request.UserUpdateRequestDto;
import com.example.userservice.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto request);

    UserResponseDto getUserById(Long id);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto updateUser(
            Long id,
            UserUpdateRequestDto request
    );

    void deleteUser(Long id);
}
