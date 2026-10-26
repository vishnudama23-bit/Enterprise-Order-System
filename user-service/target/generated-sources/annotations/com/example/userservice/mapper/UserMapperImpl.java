package com.example.userservice.mapper;

import com.example.userservice.dto.request.UserRequestDto;
import com.example.userservice.dto.request.UserUpdateRequestDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-20T18:35:54+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260528-0407, environment: Java 25.0.3 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.name( dto.getName() );

        return user.build();
    }

    @Override
    public void updateEntity(UserUpdateRequestDto dto, User user) {
        if ( dto == null ) {
            return;
        }

        user.setEmail( dto.getEmail() );
        user.setName( dto.getName() );
    }

    @Override
    public UserResponseDto toResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto.UserResponseDtoBuilder userResponseDto = UserResponseDto.builder();

        userResponseDto.createdDate( user.getCreatedDate() );
        userResponseDto.email( user.getEmail() );
        userResponseDto.id( user.getId() );
        userResponseDto.name( user.getName() );
        userResponseDto.updatedDate( user.getUpdatedDate() );
        userResponseDto.version( user.getVersion() );

        return userResponseDto.build();
    }
}
