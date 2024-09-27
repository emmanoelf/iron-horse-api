package com.ironhorse.controller;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<UserResponseDto> save(UserDto userDto);
    ResponseEntity<UserResponseDto> findById(Long id);
    ResponseEntity<Void> deleteById(Long id);
    ResponseEntity<UserResponseDto> update(Long id, UserDto userDto);
}
