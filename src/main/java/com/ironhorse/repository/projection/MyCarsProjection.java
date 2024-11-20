package com.ironhorse.repository.projection;

import com.ironhorse.model.RentalStatus;

import java.math.BigDecimal;

public record MyCarsProjection(
        Long id,
        String brand,
        String model,
        Long manufactureYear,
        String city,
        Double rate,
        Integer numberTrips,
        BigDecimal price,
        String path,
        Long rentalId,
        RentalStatus status
) {
}
