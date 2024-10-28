package com.ironhorse.controller;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Rental controller")
public interface RentalController {

    @Operation(summary = "Create a new rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create rental",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))}),
    })
    ResponseEntity<RentalResponseDto> save(RentalDto rentalDto, Long carId);
}
