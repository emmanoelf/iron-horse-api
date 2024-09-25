package com.ironhorse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserResponseDto (
        Long id,
        String email,
        String phone,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime created_at,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updated_at
){}
