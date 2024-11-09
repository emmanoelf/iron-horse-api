package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ironhorse.model.RentalStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RentalResponseDetailsDto(
        Long id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime expectedEndDate,

        RentalStatus status,
        Long carId,
        String carBrand,
        String carModel,
        Long carManufactureYear,
        BigDecimal price,
        int daysRented,
        BigDecimal totalPrice
) {
}


