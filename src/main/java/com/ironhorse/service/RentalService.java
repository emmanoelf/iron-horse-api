package com.ironhorse.service;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;

public interface RentalService {
    RentalResponseDto save(RentalDto rentalDto, Long carId);
}
