package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RentalResponseDto(
        Long id,
        LocalDateTime startDate,
        LocalDateTime expectedEndDate,
        LocalDateTime realEndDate,
        String status,
        Long carId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt,
        String url
) {
}
