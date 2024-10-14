package com.ironhorse.repository;

import com.ironhorse.dto.ReviewDto;
import com.ironhorse.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
