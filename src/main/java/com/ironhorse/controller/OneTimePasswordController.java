package com.ironhorse.controller;

import com.ironhorse.dto.OneTimePasswordValidationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "One Time Password Controller")
public interface OneTimePasswordController {

    @Operation(summary = "Generate One Time Password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Generate One Time Password by rentalId and put informations to validate " +
                            "if information in database matches with this One Time Password",
                    content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<String> generateOneTimePassword(Long rentalId);

    @Operation(summary = "Validate One Time Passsword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Send one time password through request to validate if matches stored database",
                    content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<Boolean> validateOneTimePassword(OneTimePasswordValidationDto oneTimePasswordValidationDto);
}
