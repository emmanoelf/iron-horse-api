package com.ironhorse.dto.googleGeoCode;

import java.util.List;

public record GeocodeResponseDto(
        List<ResultDto> results
) {
}
