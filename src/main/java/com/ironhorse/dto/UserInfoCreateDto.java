package com.ironhorse.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserInfoCreateDto(
        @NotBlank @NotNull String cpf,
        @NotBlank @NotNull String streetAddress,
        @NotBlank @NotNull String streetName,
        Long streetNumber,
        Boolean acceptComunication,

        @AssertTrue(message = "Você deve aceitar os Termos de Uso prosseguir com o cadastro.")
        Boolean isTermsUser,

        @AssertTrue(message = "Você deve informar que está regularizado quanto aos órgãos de trânsito prosseguir com o cadastro.")
        Boolean isRegularized,

        @AssertTrue(message = "Você deve estar ciente que as informações são legítimas.")
        Boolean isRealInformation,

        @NotBlank @NotNull String district,
        @NotBlank @NotNull String zipcode,
        @NotBlank @NotNull String city,
        @NotBlank @NotNull String state,
        @NotBlank @NotNull String driverLicense
) {
}
