package com.ironhorse.service;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;

public interface CarService {
    CarResponseDto save(CarDto carDto, Long id);
}
