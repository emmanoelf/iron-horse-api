package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CarOverviewCreateDto(
        @NotNull @NotBlank String description,
        boolean isActive,
        boolean isAvailable,
        @NotNull BigDecimal price
) {
}
