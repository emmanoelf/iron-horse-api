package com.ironhorse.controller;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewListDto;
import com.ironhorse.dto.CarOverviewResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface CarOverviewController {
        @Operation(summary = "Create a new car overview")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201",
                        description = "Car overview created",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarOverviewCreateDto.class))}),
        })
        ResponseEntity<CarOverviewResponseDto> createCarOverview(CarOverviewCreateDto carOverviewCreateDto, Long carId);

        @Operation(summary = "Update car overview")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200",
                        description = "Car overview updated",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarOverviewCreateDto.class))}),
        })
        ResponseEntity<CarOverviewResponseDto> updateCarOverview(CarOverviewCreateDto carOverviewCreateDto, Long carId);

        @Operation(summary = "Get car overview")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200",
                        description = "Get car overview by id with all details",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarOverviewListDto.class))}),
        })
        ResponseEntity<CarOverviewListDto> getCarOverviewByCarId(@PathVariable Long carId);
}