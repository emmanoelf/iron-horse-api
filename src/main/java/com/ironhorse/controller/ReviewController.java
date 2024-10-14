package com.ironhorse.controller;

import com.ironhorse.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewController {

    ResponseEntity<List<ReviewDto>> getReviewListById(Long carId);
}
