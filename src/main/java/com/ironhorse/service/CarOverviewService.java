package com.ironhorse.service;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewResponseDto;
import com.ironhorse.model.CarOverview;

import java.util.List;

public interface CarOverviewService {
    CarOverviewResponseDto save(CarOverviewCreateDto carOverviewCreateDto, Long carId);
    CarOverview findById(Long id);
    void deleteById(Long id);
    CarOverview getAllDetails(Long carId);
}