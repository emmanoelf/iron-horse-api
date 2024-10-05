package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserInfoResponseDto(
        String cpf,
        String streetAddress,
        String streetName,
        Long streetNumber,
        String district,
        String zipcode,
        String city,
        String state,
        String driverLicense,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime created_at
) {
}
