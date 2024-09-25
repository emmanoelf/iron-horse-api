package com.ironhorse.mapper;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.model.User;

public class UserMapper {

    public static User toModel(UserDto userDto) {
        return User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(userDto.password())
                .phone(userDto.phone())
                .build();
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getPhone(),
                user.getCreated_at(),
                user.getUpdated_at()
        );
    }
}
