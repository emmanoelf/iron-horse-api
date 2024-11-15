package com.ironhorse.service;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.model.User;

public interface UserService {
    UserResponseDto save(UserDto userDto);
    UserResponseDto findById(Long id);
    Long deleteById(Long id);
    UserResponseDto update(Long id, UserDto userDto);
    User findByEmail(String email);
}
