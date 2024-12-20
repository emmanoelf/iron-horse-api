package com.ironhorse.service.impl;

import com.ironhorse.dto.ReviewDto;
import com.ironhorse.exception.CarNotFound;
import com.ironhorse.mapper.ReviewMapper;
import com.ironhorse.model.*;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.repository.ReviewRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;
    private final RentalRepository rentalRepository;

    @Transactional
    public ReviewDto createReview(ReviewDto reviewDto, Long carId) {
        Long userId = this.authenticatedService.getCurrentUserId();
        Car car = this.carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado!"));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado!"));

        Set<RentalStatus> statuses = Set.of(RentalStatus.FINISHED, RentalStatus.FINISHED_LATE);
        this.rentalRepository.findByUserIdAndCarIdAndStatusIn(userId, carId, statuses)
                .orElseThrow(() -> new IllegalArgumentException("O usuário só pode avaliar um carro após a locação ser finalizada."));

        Review review = ReviewMapper.toModel(reviewDto);
        review.setUser(user);
        review.setCar(car);
        Review savedReview = repository.save(review);
        return ReviewMapper.toDto(savedReview);
    }

    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = repository.findAll();
        return reviews.stream()
                .map(ReviewMapper::toDto)
                .toList();
    }

    public ReviewDto getReviewById(Long id) {
        return repository.findById(id).map(ReviewMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));

    }

    public List<ReviewDto> getReviewListByCarId(Long carId) {
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

    @Transactional
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        Review review = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));
        review.setPros(reviewDto.pros());
        review.setCons(reviewDto.cons());
        review.setRate(reviewDto.rate());

        Review updatedReview = repository.save(review);
        return ReviewMapper.toDto(updatedReview);
    }

    @Transactional
    public void deleteReview(Long carId) {
        repository.deleteById(carId);
    }

}