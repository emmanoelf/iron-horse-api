package com.ironhorse.controller;

import com.ironhorse.dto.FileStorageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Upload File Controller")
public interface FileStorageController {

    @Operation(summary = "Upload file user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Upload a file for user",
                    content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<Void> uploadFile(MultipartFile file);

    @Operation(summary = "Delete file user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Delete file",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FileStorageDto.class))}),
    })
    ResponseEntity<Void> deleteUserProfileFile();

    @Operation(summary = "Get specific file from user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get specific file from user id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FileStorageDto.class))}),
    })
    ResponseEntity<FileStorageDto> getUserProfile();
}
