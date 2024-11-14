package com.ironhorse.dto;

import java.util.Map;

public record EmailDto(
        String to,
        String subject,
        String body,
        Map<String, String> templateVariables
) {
}
