package com.ironhorse.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarInfoDto(
        @NotNull @NotBlank String licensePlate,
        @NotNull @NotBlank String transmission,
        @NotNull @NotBlank String directionType,
        @NotNull @NotBlank String chassi,
        @NotNull @NotBlank String engineNumber,
        @NotNull @NotBlank String engineHorsePower,
        @NotNull @NotBlank String mileage,
        @NotNull @NotBlank String fuelType,
        @NotNull @NotBlank String renavam,
        String  insuranceName,
        Boolean insurance,
        Boolean insulfilm,
        Boolean tagPike,
        Boolean antiTheftSecret,
        Boolean multimedia,
        Boolean airConditioner,
        Boolean electricWindowsAndLocks,
        Boolean triangle,
        Boolean monkey,
        Boolean wheelWrench,
        Boolean spareTire,
        Boolean fireExtinguisher,
        Boolean alarm,
        Boolean smokersAccepted,
        Boolean tagActivated,
        Boolean isFinesBelongToTheOffender,
        @AssertTrue(message = "Você deve aceitar os Termos de Uso prosseguir com o cadastro.")
        Boolean isTermsUser
        ) {
}