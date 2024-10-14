package com.ironhorse.mapper;

import com.ironhorse.dto.ReviewDto;
import com.ironhorse.model.Review;

public class ReviewMapper {

    public static Review toModel(ReviewDto reviewDto) {
        return Review.builder()
                .rate(reviewDto.rate())
                .pros(reviewDto.pros())
                .cons(reviewDto.cons())
                .build();
    }

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getRate(),
                review.getPros(),
                review.getCons(),
                review.getCreated_at(),
                review.getUpdated_at()
        );
    }
}
