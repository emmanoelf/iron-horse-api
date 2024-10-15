package com.ironhorse.service;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.repository.projection.CarResumeProjection;

import java.util.List;

public interface CarService {
    CarResponseDto save(CarDto carDto, Long id);
    CarResponseDto findById(Long id);
    Long deleteById(Long id);
    CarResponseDto update(CarDto carDto, Long carId, Long userId);
    List<CarResumeProjection> findAllCarsByCity(String city);
}
