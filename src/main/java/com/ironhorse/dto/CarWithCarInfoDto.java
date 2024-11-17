package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CarWithCarInfoDto(
        @NotNull @NotBlank String brand,
        @NotNull @NotBlank String model,
        @NotNull Long manufactureYear,
        List<ReviewDto> reviews,
        CarInfoUpdateDto carInfo
) {
}
