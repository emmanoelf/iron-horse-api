package com.ironhorse.dto;

import java.util.List;

public record PaymentDto(
        String name,
        Long quantity,
        Long totalPrice,
        String description,
        List<String> images
) {
}
