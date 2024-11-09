package com.ironhorse.mapper;

import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDetailsDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.model.Rental;
import com.ironhorse.repository.projection.RentalDetailsProjection;

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

    public static RentalResponseDetailsDto toDto(RentalDetailsProjection rentalDetailsProjection) {
        return new RentalResponseDetailsDto(
                rentalDetailsProjection.getId(),
                rentalDetailsProjection.getStartDate(),
                rentalDetailsProjection.getExpectedEndDate(),
                rentalDetailsProjection.getStatus(),
                rentalDetailsProjection.getCarId(),
                rentalDetailsProjection.getCarBrand(),
                rentalDetailsProjection.getCarModel(),
                rentalDetailsProjection.getCarManufactureYear(),
                rentalDetailsProjection.getPrice(),
                rentalDetailsProjection.getDaysRented(),
                rentalDetailsProjection.getTotalPrice()
        );
    }
}
