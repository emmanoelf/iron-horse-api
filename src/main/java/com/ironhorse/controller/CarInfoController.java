package com.ironhorse.controller;

import com.ironhorse.dto.CarInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;

@Tag(name = "Car Info Controller")
public interface CarInfoController {
    @Operation(summary = "Create Car info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create car",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<CarInfoDto> save(CarInfoDto carDto, Long carId);

    @Operation(summary = "Find car info by CarId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Car info found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarInfoDto.class))}),
    })
    ResponseEntity<CarInfoDto> findCarById(Long id);

    @Operation(summary = "Delete car info by carID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Car Info deleted"),
    })
    ResponseEntity<Void> deleteByCarId(Long id);

    @Operation(summary = "Update car info by carID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Car updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarInfoDto.class))}),
    })
    ResponseEntity<CarInfoDto> update(CarInfoDto carInfoDto, Long carId);

}
