package com.ironhorse.service;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewResponseDto;
import com.ironhorse.model.CarOverview;

public interface CarOverviewService {
    CarOverviewResponseDto save(CarOverviewCreateDto carOverviewCreateDto, Long carId);
    CarOverview findById(Long id);
    void deleteById(Long id);
    CarOverviewResponseDto update(CarOverviewCreateDto carOverviewCreateDto, Long carId);
}