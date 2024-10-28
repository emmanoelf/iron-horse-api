package com.ironhorse.mapper;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.model.Rental;

public class RentalMapper {

    public static Rental toModel(RentalDto rentalDto) {
        return Rental.builder()
                .startDate(rentalDto.startDate())
                .expectedEndDate(rentalDto.expectedEndDate())
                .build();
    }

    public static RentalResponseDto toDto(Rental rental) {
        return new RentalResponseDto(
                rental.getStartDate(),
                rental.getExpectedEndDate(),
                rental.getStatus().toString(),
                rental.getCar().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }
}
