package com.ironhorse.controller;

import com.ironhorse.dto.ReviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Review Controller")
public interface ReviewController {

    @Operation(summary = "Get reviews list by car ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get review list by ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))}),
    })
    ResponseEntity<List<ReviewDto>> getReviewListByCarId(Long carId);

    @Operation(summary = "Get all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get all reviews",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))}),
    })
    ResponseEntity<List<ReviewDto>> getAllReviews();

    @Operation(summary = "Create review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Create review by car ID and User ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))}),
    })
    ResponseEntity<ReviewDto> createReview(ReviewDto reviewDto, Long carId);

    @Operation(summary = "Get review by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get review by ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))}),
    })
    ResponseEntity<ReviewDto> getReviewById(Long carId);

    @Operation(summary = "Update review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Update review by ID",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))}),
    })
    ResponseEntity<ReviewDto> updateReview(Long id,ReviewDto reviewDto);

    @Operation(summary = "Delete review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Delete review by ID",
                    content = {@Content(mediaType = "application/json")}),
    })
    ResponseEntity<Void> deleteReview(Long id);

}
