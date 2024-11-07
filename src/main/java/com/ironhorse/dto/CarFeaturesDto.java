package com.ironhorse.dto;

import jakarta.validation.constraints.AssertTrue;

public record CarFeaturesDto(
        Boolean insulfilm,
        Boolean tagPike,
        Boolean antiTheftSecret,
        Boolean multimedia,
        Boolean airConditioner,
        Boolean electricWindowsAndLocks,
        Boolean triangle,
        Boolean jack,
        Boolean wheelWrench,
        Boolean spareTire,
        Boolean fireExtinguisher,
        Boolean alarm
//        Boolean smokersAccepted,
//        Boolean tagActivated,
//        Boolean isFinesBelongToTheOffender,
//        @AssertTrue(message = "Os documentos devem estar em dia para prosseguir com o cadastro.")
//        Boolean isDocsUptoDate,
//        Boolean isVeicleModified,
//        @AssertTrue(message = "As informações devem ser verdadeiras para prosseguir com o cadastro.")
//        Boolean isTrueInformation
) {
}
