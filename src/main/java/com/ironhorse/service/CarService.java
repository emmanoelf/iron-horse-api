package com.ironhorse.service;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.repository.projection.CarResumeProjection;

import java.util.List;

public interface CarService {
    CarSaveDto save(CarSaveDto carDto);
    CarResponseDto findById(Long id);
    Long deleteById(Long id);
    CarResponseDto update(CarDto carDto, Long carId, Long userId);
    List<CarResumeProjection> findAllCarsByCity(String city);
}
