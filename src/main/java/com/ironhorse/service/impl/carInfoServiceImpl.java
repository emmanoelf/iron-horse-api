package com.ironhorse.service.impl;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.exception.CarNotFound;
import com.ironhorse.mapper.CarInfoMapper;
import com.ironhorse.mapper.CarMapper;
import com.ironhorse.repository.CarInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class carInfoServiceImpl {

 private final CarInfoRepository carInfoRepository;

    @Override
    public CarInfoDto findById(Long id) {
        return this.carInfoRepository.findById(id)
                .map(CarInfoMapper::toDto)
                .orElseThrow(
                        () -> new CarNotFound("Carro não encontrado")
                );
    }

}