package com.ironhorse.controller;

import com.ironhorse.dto.CarInfoConsentsDto;
import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.FileStorageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Car Info Controller")
public interface CarInfoController {

    @Operation(summary = "Create Car info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create car",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<CarInfoDto> save(CarInfoDto carInfoDto,  Long carId);

    @Operation(summary = "Create car images with consents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Save car images with consents",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<Void> saveCarImages( List<MultipartFile> files,  Long id,  CarInfoConsentsDto carInfoConsentsDto);


    @Operation(summary = "Delete car images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Delete car images",
                    content = {@Content(mediaType = "application/json")})
    })
    ResponseEntity<Void> deleteCarImageFile(Long carId);

    @Operation(summary = "Find car images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Find car images with car id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FileStorageDto.class))}),
    })
    ResponseEntity<List<FileStorageDto>> getCarImages(Long carId);

    @Operation(summary = "Find car info by CarId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Car info found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarInfoDto.class))}),
    })
    ResponseEntity<CarInfoDto> findInfoByCarId(Long id);

    @Operation(summary = "Delete car info by car id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Car Info deleted"),
    })
    ResponseEntity<Void> deleteByCarId(Long id);

    @Operation(summary = "Update car info by car id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Update car with all informations except images",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CarInfoDto.class))}),
    })
    ResponseEntity<CarInfoDto> update(CarInfoDto carInfoDto, Long carId);

}
