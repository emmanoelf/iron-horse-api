package com.ironhorse.service;

import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.model.CarOverview;

import java.util.List;

public interface CarOverviewService {
    CarOverview save(CarOverview carOverview);

    List<CarOverviewDto> findAll();

    CarOverview findById(Long id);
    void deleteById(Long id);
}