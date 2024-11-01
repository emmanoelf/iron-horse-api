package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarInfoDto(
        @NotNull @NotBlank String renavam,
        @NotNull @NotBlank String licensePlate,
        @NotNull @NotBlank String transmission,
        @NotNull @NotBlank String directionType,
        @NotNull @NotBlank String chassi,
        @NotNull @NotBlank String engineNumber,
        @NotNull @NotBlank String cylinderDisplacement,
        @NotNull @NotBlank String mileage,
        @NotNull @NotBlank String fuelType
        ) {
}