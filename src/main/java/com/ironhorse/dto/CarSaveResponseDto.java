package com.ironhorse.dto;

public record CarSaveResponseDto(
        Long id,
        String brand,
        String model,
        Long manufactureYear,
        CarInfoDto carInfoDto
) {
}
