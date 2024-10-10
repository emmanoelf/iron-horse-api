package com.ironhorse.controller;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.model.CarOverview;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface CarOverviewController {
    @Operation(summary = "Create Car info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create car",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<CarOverviewDto> save(CarOverviewDto carOverviewDtoDto, Long carId);

    @Operation(summary = "Find car info by CarId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Car info found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarInfoDto.class))}),
    })
    ResponseEntity<CarOverviewDto> findCarById(Long id);

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
    ResponseEntity<CarOverviewDto> update(CarOverviewDto carOverviewDto, Long carId);

    ResponseEntity<CarOverviewDto> findAll();
}
