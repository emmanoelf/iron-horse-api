package com.ironhorse.service;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;

public interface UserService {
    UserResponseDto save(UserDto userDto);
    UserResponseDto findById(Long id);
}
