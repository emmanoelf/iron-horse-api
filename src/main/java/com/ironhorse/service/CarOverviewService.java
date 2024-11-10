package com.ironhorse.service;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewListDto;
import com.ironhorse.dto.CarOverviewResponseDto;

public interface CarOverviewService {
    CarOverviewResponseDto save(CarOverviewCreateDto carOverviewCreateDto, Long carId);
    CarOverviewListDto findByCarIdWithDetails(Long carId);
    CarOverviewResponseDto update(CarOverviewCreateDto carOverviewCreateDto, Long carId);
    void setIsActive(Long carId, boolean isActive);
    void setIsAvailable(Long carId, boolean isAvailable);
    void increaseNumberOfTrips(Long carId);
}