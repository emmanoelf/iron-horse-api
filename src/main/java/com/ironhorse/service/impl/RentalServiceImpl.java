package com.ironhorse.service.impl;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.mapper.RentalMapper;
import com.ironhorse.model.Car;
import com.ironhorse.model.Rental;
import com.ironhorse.repository.CarRepository;
import com.ironhorse.repository.RentalRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.AuthenticatedService;
import com.ironhorse.service.RentalService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;

    @Override
    @Transactional
    public RentalResponseDto save(RentalDto rentalDto, Long carId) {
        Long userId = this.authenticatedService.getCurrentUserId();
        Car car = this.carRepository.findById(carId).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado"));

        Rental rental = RentalMapper.toModel(rentalDto);
        rental.setCar(car);
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
}
