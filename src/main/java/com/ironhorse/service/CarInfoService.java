package com.ironhorse.service;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarResponseDto;

public interface CarInfoService {
    CarInfoDto save(CarInfoDto carDto, Long id);
    CarInfoDto findById(Long id);
    Long deleteById(Long id);
    CarInfoDto update(CarInfoDto carDto, Long carId, Long userId);
}
