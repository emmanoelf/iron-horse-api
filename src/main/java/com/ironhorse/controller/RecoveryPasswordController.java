package com.ironhorse.controller;

import com.ironhorse.dto.EmailRecoveryDto;
import com.ironhorse.dto.EmailResetPassword;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Password Recovery Controller")
public interface RecoveryPasswordController {
    @Operation(summary = "Request password recovery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request password recovery link",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
    })
    ResponseEntity<Void> createLinkRecovery(@RequestBody EmailRecoveryDto email);

    @Operation(summary = "Reset password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reset password by token sent to email and define new chosen password",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
    })
    ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestBody EmailResetPassword password);
}
