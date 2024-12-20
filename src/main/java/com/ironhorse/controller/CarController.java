package com.ironhorse.controller;

import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.dto.CarSaveResponseDto;
import com.ironhorse.dto.CarUpdateDto;
import com.ironhorse.repository.projection.CarResumeProjection;
import com.ironhorse.repository.projection.MyCarsProjection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Tag(name = "Car Controller")
public interface CarController {

    @Operation(summary = "Create Car with all informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create car",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarSaveDto.class))})
    })
    ResponseEntity<CarSaveResponseDto> save(CarSaveDto carSaveDto);

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
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarUpdateDto.class))}),
    })
    ResponseEntity<CarUpdateDto> update(CarUpdateDto carUpdateDto, Long carId);

    @Operation(summary = "Get all cars by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get all cars resume to list by city",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarResumeProjection.class))}),
    })
    Page<CarResumeProjection> findAllCarsByCity(String city, LocalDateTime startDate, LocalDateTime endDate, int page, int size);

    @Operation(summary = "Get all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get all cars by user logged with rentals if they exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyCarsProjection.class))}),
    })
    ResponseEntity<Optional<List<MyCarsProjection>>> findAllCars();
}
