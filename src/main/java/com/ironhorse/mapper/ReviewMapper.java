package com.ironhorse.mapper;

import com.ironhorse.dto.ReviewDto;
import com.ironhorse.model.Review;

public class ReviewMapper {

    public static Review toModel(ReviewDto reviewDto){
        return Review.builder()
                .rate(reviewDto.rate())
                .numberOfRented(reviewDto.numberOfRented())
                .pros(reviewDto.pros())
                .build();
    }

    public static ReviewDto toDto(Review review){
        return new ReviewDto(
                review.getRate(),
                review.getNumberOfRented(),
                review.getPros()
        );
    }
}
