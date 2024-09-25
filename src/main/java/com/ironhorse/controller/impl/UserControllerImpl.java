package com.ironhorse.controller.impl;

import com.ironhorse.controller.UserController;
import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid UserDto userDto) {
        UserResponseDto userResponseDto = this.userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto userResponseDto = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
