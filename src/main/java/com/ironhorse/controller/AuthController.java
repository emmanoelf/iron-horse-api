package com.ironhorse.controller;

import com.ironhorse.dto.AuthenticationDto;
import com.ironhorse.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication Controller")
public interface AuthController {

    @Operation(summary = "Authenticate an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Authenticate an user with email and password",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))})
    })
    ResponseEntity<TokenDto> authenticate(AuthenticationDto authenticationDto);

    @Operation(summary = "Refresh user token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Renew an existing token",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))})
    })
    ResponseEntity<TokenDto> refreshTokenAccess(String refreshToken);
}
