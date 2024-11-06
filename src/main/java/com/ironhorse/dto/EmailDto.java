package com.ironhorse.dto;

public record EmailDto(
        String to,
        String subject,
        String body
) {
}
