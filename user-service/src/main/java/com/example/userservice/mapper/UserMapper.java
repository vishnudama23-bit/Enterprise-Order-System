
package com.example.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.userservice.dto.request.UserRequestDto;
import com.example.userservice.dto.request.UserUpdateRequestDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    User toEntity(UserRequestDto dto);

   
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntity(
            UserUpdateRequestDto dto,
            @MappingTarget User user
    );

   
    UserResponseDto toResponseDto(User user);
}
