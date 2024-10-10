package com.ironhorse.dto;

import com.ironhorse.model.CarInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CarOverviewCarDto(
        @NotNull @NotBlank String description,
        @NotNull Integer numberTrips,
        boolean isActive,
        boolean isAvailable,
        @NotNull BigDecimal price,
        @NotNull @NotBlank String brand,
        @NotNull @NotBlank String model,
        @NotNull @NotBlank CarInfoDto carInfos
) {
}
