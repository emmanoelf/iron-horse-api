package com.ironhorse.service;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewListDto;
import com.ironhorse.dto.CarOverviewResponseDto;

public interface CarOverviewService {
    CarOverviewResponseDto save(CarOverviewCreateDto carOverviewCreateDto, Long carId);
    CarOverviewListDto findByCarIdWithDetails(Long carId);
    CarOverviewResponseDto update(CarOverviewCreateDto carOverviewCreateDto, Long carId);
}