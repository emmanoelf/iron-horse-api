package com.ironhorse.dto;

public record PaymentDto(
        Long id,
        String name,
        Long quantity,
        Long totalPrice,
        String description,
        String image
) {
}
