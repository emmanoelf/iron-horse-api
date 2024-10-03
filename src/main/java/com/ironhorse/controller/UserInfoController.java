package com.ironhorse.controller;

import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Info Controller")
public interface UserInfoController {

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create a user info",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<UserInfoResponseDto> save(UserInfoDto userInfoDto, Long userId);

    @Operation(summary = "Find user info by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User info found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponseDto.class))}),
    })
    ResponseEntity<UserInfoResponseDto> findByUserId(Long userId);

    @Operation(summary = "Delete user info by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User info deleted"),
    })
    ResponseEntity<Void> deleteByUserId(Long userId);
}
