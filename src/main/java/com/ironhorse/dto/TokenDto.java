package com.ironhorse.dto;

public record TokenDto(
        String accessToken,
        String refreshToken
) {
}
