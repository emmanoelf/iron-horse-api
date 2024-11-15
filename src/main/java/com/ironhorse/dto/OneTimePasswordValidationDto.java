package com.ironhorse.dto;

public record OneTimePasswordValidationDto(
        Long rentalId,
        String oneTimePassword
) {
}
