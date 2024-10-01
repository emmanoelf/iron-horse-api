package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarDto(
        @NotNull @NotBlank String brand,
        @NotNull @NotBlank String model,
        @NotNull Long manufactureYear
) {
}
