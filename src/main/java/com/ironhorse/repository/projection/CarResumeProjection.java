package com.ironhorse.repository.projection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CarResumeProjection(
        Long id,
        @NotBlank @NotNull String brand,
        @NotBlank @NotNull String model,
        @NotNull Long manufactureYear,
        @NotBlank @NotNull String city,
        @NotBlank @NotNull String state,
        @NotBlank @NotNull String streetAddress,
        @NotBlank @NotNull String streetName,
        Long streetNumber,
        Double rate,
        Integer numberTrips,
        BigDecimal price
) {
}