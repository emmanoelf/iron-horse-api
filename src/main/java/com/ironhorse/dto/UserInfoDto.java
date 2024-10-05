package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInfoDto(
        @NotBlank @NotNull String cpf,
        @NotBlank @NotNull String streetAddress,
        @NotBlank @NotNull String streetName,
        Long streetNumber,
        @NotBlank @NotNull String district,
        @NotBlank @NotNull String zipcode,
        @NotBlank @NotNull String city,
        @NotBlank @NotNull String state,
        @NotBlank @NotNull String driverLicense
) {
}
