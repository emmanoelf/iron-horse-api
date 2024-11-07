package com.ironhorse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarSaveDto(
        @NotNull @NotBlank String brand,
        @NotNull @NotBlank String model,
        @NotNull(message = "O ano de fabricação não pode ser nulo.")
        @Min(value = 1900, message = "O ano de fabricação deve ser maior ou igual a 1900.")
        Long manufactureYear,
        @NotNull CarInfoDto carInfoDto
) {
}
