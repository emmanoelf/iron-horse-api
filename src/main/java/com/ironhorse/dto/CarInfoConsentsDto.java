package com.ironhorse.dto;

import jakarta.validation.constraints.AssertTrue;

public record CarInfoConsentsDto(
            Boolean smokersAccepted,
            Boolean tagActivated,
            Boolean finesBelongToTheOffender,
            Boolean veicleModified,
            @AssertTrue(message = "As informações devem ser verdadeiras para prosseguir com o cadastro.")
            Boolean trueInformation,
            @AssertTrue(message = "As informações devem ser verdadeiras para prosseguir com o cadastro.")
            Boolean docsUptoDate
    ) {
    }