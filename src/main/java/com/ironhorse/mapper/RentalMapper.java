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
                rental.getId(),
                rental.getStartDate(),
                rental.getExpectedEndDate(),
                rental.getRealEndDate(),
                rental.getStatus().toString(),
                rental.getCar().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt(),
                null
        );
    }

    public static RentalResponseDto toDtoWithPaymentUrl(Rental rental, String paymentUrl) {
        return new RentalResponseDto(
                rental.getId(),
                rental.getStartDate(),
                rental.getExpectedEndDate(),
                rental.getRealEndDate(),
                rental.getStatus().toString(),
                rental.getCar().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt(),
                paymentUrl
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
