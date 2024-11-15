package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarInfoDto(
        @NotBlank Boolean insurance,
        @NotNull @NotBlank String insuranceName,
        @NotNull @NotBlank String renavam,
        @NotNull @NotBlank String licensePlate,
        @NotNull @NotBlank String transmission,
        @NotNull @NotBlank String directionType,
        @NotNull @NotBlank String chassi,
        @NotNull @NotBlank String engineNumber,
        @NotNull @NotBlank String cylinderDisplacement,
        @NotNull @NotBlank String mileage,
        @NotNull @NotBlank String fuelType,
        @NotNull @NotBlank String color,
        @NotNull @NotBlank Long numDoors,
        @NotNull @NotBlank Long numSeats,
        @NotNull @NotBlank String headlightBulb,
        @NotNull @NotBlank String trunkCapacity,
        @NotNull CarFeaturesDto carFeaturesDto
        ) {
}