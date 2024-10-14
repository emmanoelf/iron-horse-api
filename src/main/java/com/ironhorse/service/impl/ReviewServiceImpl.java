package com.ironhorse.service.impl;

import com.ironhorse.dto.ReviewDto;
import com.ironhorse.exception.CarNotFound;
import com.ironhorse.mapper.ReviewMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.Review;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.ReviewRepository;
import com.ironhorse.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final CarRepository carRepository;

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
    public ReviewDto getReviewById(Long id) {
        return repository.findById(id).map(ReviewMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));

    }

    // Review por carro
    public List<ReviewDto> getReviewByCarId(Long carId) {
        Car car = this.carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFound("Carro não encontrado"));
        List<ReviewDto> reviewDtos = Optional.ofNullable(car.getReviews())
                .filter(reviews -> !reviews.isEmpty())
                .orElseThrow(() -> new EntityNotFoundException("No reviews found for this car") {
                })
                .stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
        return reviewDtos;

    }

    // Atualizar Review
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review review = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));
        review.setPros(reviewDto.pros());
        review.setCons(reviewDto.cons());

        Review updatedReview = repository.save(review);
        return ReviewMapper.toDto(updatedReview);
    }

    // Deletar Review
    public void deleteReview(Long carId) {

        repository.deleteById(carId);
    }

}
