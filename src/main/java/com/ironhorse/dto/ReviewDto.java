package com.ironhorse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewDto(
        @NotNull Long rate,
        @NotNull Long numberOfRented,
        @NotNull @NotBlank String pros
) {
}
