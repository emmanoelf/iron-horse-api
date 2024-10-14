package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReviewDto(
        @NotNull Long rate,
        @NotNull @NotBlank String pros,
        @NotNull @NotBlank String cons

//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
//        LocalDateTime created_at,
//
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
//        LocalDateTime updated_at

) {
}
