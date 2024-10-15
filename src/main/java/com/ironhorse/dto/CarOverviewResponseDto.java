package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CarOverviewResponseDto(
        @NotNull @NotBlank String description,
        @NotNull Integer numberTrips,
        boolean isActive,
        boolean isAvailable,
        @NotNull BigDecimal price,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime created_at,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updated_at
) {
}
