package com.ironhorse.service;

import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.dto.CarSaveResponseDto;
import com.ironhorse.dto.CarUpdateDto;
import com.ironhorse.repository.projection.CarResumeProjection;
import org.springframework.data.domain.Page;

public interface CarService {
    CarSaveResponseDto save(CarSaveDto carDto);
    CarResponseDto findById(Long id);
    Long deleteById(Long id);
    CarUpdateDto update(CarUpdateDto carSaveDto, Long carId);
    Page<CarResumeProjection> findAllCarsByCity(String city, int page, int size);
}
