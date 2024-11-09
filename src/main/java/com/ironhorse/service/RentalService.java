package com.ironhorse.service;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDetailsDto;
import com.ironhorse.dto.RentalResponseDto;

import java.util.List;

public interface RentalService {
    RentalResponseDto save(RentalDto rentalDto, Long carId);
    List<RentalResponseDto> getAllRentalsByLoggedUser();
    void cancelRental(Long rentalId);
    RentalResponseDetailsDto getRentalDetails(Long rentalId);
    RentalResponseDto finishRental(Long rentalId);
}