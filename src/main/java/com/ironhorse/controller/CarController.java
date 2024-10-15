package com.ironhorse.controller;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.repository.projection.CarResumeProjection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Car Controller")
public interface CarController {

    @Operation(summary = "Create Car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create car",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<CarResponseDto> save(CarDto carDto, Long id);

    @Operation(summary = "Find car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Car found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarResponseDto.class))}),
    })
    ResponseEntity<CarResponseDto> findById(Long id);

    @Operation(summary = "Delete car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Car deleted"),
    })
    ResponseEntity<Void> deleteById(Long id);

    @Operation(summary = "Update car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Car updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarResponseDto.class))}),
    })
    ResponseEntity<CarResponseDto> update(CarDto carDto, Long carId, Long userId);

    @Operation(summary = "Get all cars by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get all cars resume to list by city",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarResumeProjection.class))}),
    })
    ResponseEntity<List<CarResumeProjection>> findAllCarsByCity(String city);
}
