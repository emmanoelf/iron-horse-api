package com.ironhorse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CarOverviewDto(
        String model,
        String brand,
        String description,
        Integer numberTrips,
        boolean isActive,
        boolean isAvailable,
        Long year,
        BigDecimal price,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        CarDto car // Referência ao CarDTO
) {}
