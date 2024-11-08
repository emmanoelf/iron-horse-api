package com.ironhorse.service;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.dto.CarUpdateDto;
import com.ironhorse.repository.projection.CarResumeProjection;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CarService {
    CarSaveDto save(CarSaveDto carDto);
    CarResponseDto findById(Long id);
    Long deleteById(Long id);

    CarUpdateDto update(CarUpdateDto carSaveDto, Long carId);

    List<CarResumeProjection> findAllCarsByCity(String city);
}
