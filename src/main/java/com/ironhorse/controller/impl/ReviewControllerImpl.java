package com.ironhorse.controller.impl;

import com.ironhorse.controller.ReviewController;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.ReviewDto;
import com.ironhorse.model.Car;
import com.ironhorse.service.CarService;
import com.ironhorse.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/reviews")
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;


    public ReviewControllerImpl(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/{carId}/{userId}")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto,@PathVariable Long carId, @PathVariable Long userId) {
        ReviewDto createdReview = reviewService.createReview(reviewDto, carId, userId);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Object> getReviewById(@PathVariable Long carId) {
        ReviewDto reviewDto = this.reviewService.getReviewById(carId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
    }


    @GetMapping("/all-car-reviews/{carId}")
    public ResponseEntity<List<ReviewDto>> getReviewListById(@PathVariable Long carId){
        List<ReviewDto> reviewDtos = this.reviewService.getReviewByCarId(carId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDtos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(id, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
