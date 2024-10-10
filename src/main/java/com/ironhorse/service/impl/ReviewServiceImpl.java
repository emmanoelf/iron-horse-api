package com.ironhorse.service.impl;

import com.ironhorse.dto.ReviewDto;
import com.ironhorse.mapper.ReviewMapper;
import com.ironhorse.model.Review;
import com.ironhorse.repository.ReviewRepository;
import com.ironhorse.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final ReviewMapper reviewMapper;

    // Criar Review
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = ReviewMapper.toModel(reviewDto);
        Review savedReview = repository.save(review);
        return ReviewMapper.toDto(savedReview);
    }

    // Buscar todos os Reviews
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = repository.findAll();
        return reviews.stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    // Buscar Review por ID
    public Optional<ReviewDto> getReviewById(Long id) {
        return repository.findById(id)
                .map(ReviewMapper::toDto);
    }

    // Atualizar Review
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review review = ReviewMapper.toModel(reviewDto);
        review.setId(id); // Define o ID para garantir a atualização correta
        Review updatedReview = repository.save(review);
        return ReviewMapper.toDto(updatedReview);
    }



    // Deletar Review
    public void deleteReview(Long id) {
        repository.deleteById(id);
    }

}
