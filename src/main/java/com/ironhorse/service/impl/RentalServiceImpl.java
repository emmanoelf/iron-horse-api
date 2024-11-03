package com.ironhorse.service.impl;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.mapper.RentalMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.Rental;
import com.ironhorse.model.RentalStatus;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.CarOverviewService;
import com.ironhorse.service.RentalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;
    private final CarOverviewService carOverviewService;

    @Override
    @Transactional
    public RentalResponseDto save(RentalDto rentalDto, Long carId) {
        Long userId = this.authenticatedService.getCurrentUserId();

        this.validateDates(rentalDto.startDate(), rentalDto.expectedEndDate());

        Optional<Car> car = this.carRepository.isCarAvailableWithinDates(carId, rentalDto.startDate(), rentalDto.expectedEndDate());
        if(car.isEmpty()) {
            throw new IllegalArgumentException("Este carro não está disponível para locação na data escolhida.");
        }

        Rental rental = RentalMapper.toModel(rentalDto);
        rental.setCar(car.get());
        rental.setUser(userRepository.findById(userId).orElseThrow());

        this.rentalRepository.save(rental);
        return RentalMapper.toDto(rental);
    }

    @Override
    public List<RentalResponseDto> getAllRentalsByLoggedUser() {
        Long userId = this.authenticatedService.getCurrentUserId();
        List<Rental> rentals = this.rentalRepository.findByUserId(userId);

        return rentals.stream().map(RentalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void cancelRental(Long rentalId) {
        Long userId = this.authenticatedService.getCurrentUserId();
        Rental rental = this.rentalRepository.findById(rentalId).orElseThrow();

        if(!Objects.equals(userId, rental.getUser().getId()) || !Objects.equals(userId, rental.getCar().getUser().getId())) {
            throw new ForbiddenAccessException("Você não tem privilégios para cancelar esta locação");
        }

        this.carOverviewService.setIsAvailable(rental.getCar().getId(), true);
        rental.setStatus(RentalStatus.CANCELED);
        this.rentalRepository.save(rental);
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if(endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("A data de devolução não pode ser maior que a data de início");
        }
    }
}
