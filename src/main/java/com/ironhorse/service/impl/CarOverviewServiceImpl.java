package com.ironhorse.service.impl;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewResponseDto;
import com.ironhorse.mapper.CarOverviewMapper;
import com.ironhorse.model.Car;
import com.ironhorse.repository.CarInfoRepository;
import com.ironhorse.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ironhorse.model.CarOverview;
import com.ironhorse.repository.CarOverviewRepository;
import com.ironhorse.service.CarOverviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarOverviewServiceImpl implements CarOverviewService {
    private final CarOverviewRepository carOverviewRepository;
    private final CarRepository carRepository;
    private final CarInfoRepository carInfoRepository;

    @Override
    public CarOverviewResponseDto save(CarOverviewCreateDto carOverviewCreateDto, Long carId) {
        Car car = this.carRepository.findById(carId).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado"));

        CarOverview carOverview = CarOverviewMapper.toModel(carOverviewCreateDto);
        carOverview.setCar(car);
        this.carOverviewRepository.save(carOverview);
        return CarOverviewMapper.toDto(carOverview);
    }

    @Override
    public CarOverview findById(Long id) {
        return carOverviewRepository.findById(id).orElseThrow(() -> new RuntimeException("CarOverview not found"));
    }

    @Override
    public void deleteById(Long id) {
        carOverviewRepository.deleteById(id);
    }

    @Override
    public CarOverview getAllDetails(Long carId){
        CarOverview details = this.carOverviewRepository.findCarOverviewByCarId(carId);
        return details;
    }
}