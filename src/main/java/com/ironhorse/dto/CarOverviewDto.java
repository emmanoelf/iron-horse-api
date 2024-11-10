package com.ironhorse.dto;

import com.ironhorse.model.Car;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record CarOverviewDto(
        @NotNull @NotBlank String description,
        @NotNull Integer numberTrips,
        boolean isActive,
        boolean isAvailable,
        @NotNull BigDecimal price,
        @NotNull CarInfoDto carInfos,
        List<ReviewDto> reviews
) {
}
