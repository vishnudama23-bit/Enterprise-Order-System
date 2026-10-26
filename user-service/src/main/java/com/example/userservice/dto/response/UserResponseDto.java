
package com.example.userservice.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Integer version;
}
