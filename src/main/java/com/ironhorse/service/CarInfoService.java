package com.ironhorse.service;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.model.CarInfo;
import jakarta.transaction.Transactional;

public interface CarInfoService {
    CarInfoDto findCarById(Long carId);
    Long deleteByCarId(Long carId);
    CarInfoDto save(CarInfoDto carInfoDtoDto, Long id);
    CarInfoDto update(CarInfoDto carInfoDto, Long carId);
}
