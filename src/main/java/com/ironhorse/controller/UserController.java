package com.ironhorse.controller;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Controller")
public interface UserController {

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Create a user",
            content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<UserResponseDto> save(UserDto userDto);

    @Operation(summary = "Find user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))}),
    })
    ResponseEntity<UserResponseDto> findById(Long id);

    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User deleted"),
    })
    ResponseEntity<Void> deleteById(Long id);

    @Operation(summary = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))}),
    })
    ResponseEntity<UserResponseDto> update(Long id, UserDto userDto);
}
