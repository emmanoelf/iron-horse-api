package com.ironhorse.controller;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDetailsDto;
import com.ironhorse.dto.RentalResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Rental controller")
public interface RentalController {

    @Operation(summary = "Create a new rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create rental",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))}),
    })
    ResponseEntity<RentalResponseDto> save(RentalDto rentalDto, Long carId);

    @Operation(summary = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get all rentals by logged user id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))}),
    })
    ResponseEntity<List<RentalResponseDto>> findAllByLoggedUserId();

    @Operation(summary = "Cancel rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cancel rental if you are renter or owner",
                    content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<Void> cancelRental(Long rentalId);

    @Operation(summary = "Show details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Show more details from an rental if you are render or owner",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDetailsDto.class))}),
    })
    ResponseEntity<RentalResponseDetailsDto> findRentalById(Long rentalId);

    @Operation(summary = "Finish rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Finish an retal",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponseDto.class))}),
    })
    ResponseEntity<RentalResponseDto> finishRental(@RequestParam Long rentalId, @RequestParam String otp);
}
