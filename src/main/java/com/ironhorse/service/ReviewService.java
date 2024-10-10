package com.ironhorse.service;

import com.ironhorse.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);

    List<ReviewDto> getAllReviews();

    ReviewDto getReviewById(Long id);

    void deleteReview(Long id);

    ReviewDto updateReview(Long id, ReviewDto reviewDto);
}
