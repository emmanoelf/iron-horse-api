package com.ironhorse.service;

import com.ironhorse.dto.CarOverviewDto;

import java.util.List;
import java.util.Optional;

public interface CarOverviewService {
    CarOverviewDto createCarOverview(CarOverviewDto carOverviewDto);

    List<CarOverviewDto> getAllCarOverviews();

    Optional<CarOverviewDto> getCarOverviewById(Long id);

    CarOverviewDto updateCarOverview(Long id, CarOverviewDto carOverviewDto);

    void deleteCarOverview(Long id);
}
