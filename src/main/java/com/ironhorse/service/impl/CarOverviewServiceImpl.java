package com.ironhorse.service.impl;

import com.ironhorse.dto.CarOverviewCreateDto;
import com.ironhorse.dto.CarOverviewListDto;
import com.ironhorse.dto.CarOverviewResponseDto;
import com.ironhorse.mapper.CarOverviewMapper;
import com.ironhorse.model.Car;
import com.ironhorse.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ironhorse.model.CarOverview;
import com.ironhorse.repository.CarOverviewRepository;
import com.ironhorse.service.CarOverviewService;

@Service
@RequiredArgsConstructor
public class CarOverviewServiceImpl implements CarOverviewService {
    private final CarOverviewRepository carOverviewRepository;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public CarOverviewResponseDto save(CarOverviewCreateDto carOverviewCreateDto, Long carId) {
        Car car = this.carRepository.findById(carId).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado"));

        CarOverview carOverview = CarOverviewMapper.toModel(carOverviewCreateDto);
        carOverview.setCar(car);
        this.carOverviewRepository.save(carOverview);
        return CarOverviewMapper.toDto(carOverview);
    }

    @Override
    public CarOverviewListDto findByCarIdWithDetails(Long carId) {
        CarOverview carOverview = this.carOverviewRepository.getAllDetailsFromCarOverview(carId).orElseThrow(
                () -> new EntityNotFoundException("Carro não encontrado"));

        return CarOverviewMapper.toDtoOverview(carOverview);
    }

    @Override
    @Transactional
    public CarOverviewResponseDto update(CarOverviewCreateDto carOverviewCreateDto, Long carId) {
        CarOverview carOverview = this.carOverviewRepository.findCarOverviewByCarId(carId).orElseThrow(
                () -> new EntityNotFoundException("Detalhes do carro não encontrado"));

        carOverview.setDescription(carOverviewCreateDto.description());
        carOverview.setActive(carOverviewCreateDto.isActive());
        carOverview.setAvailable(carOverviewCreateDto.isAvailable());
        carOverview.setPrice(carOverviewCreateDto.price());

        this.carOverviewRepository.save(carOverview);
        return CarOverviewMapper.toDto(carOverview);
    }

    @Override
    @Transactional
    public void setIsActive(Long carId, boolean isActive) {
        CarOverview carOverview = this.carOverviewRepository.findCarOverviewByCarId(carId).orElseThrow(
                () -> new EntityNotFoundException("Detalhes do carro não encontrado"));

        carOverview.setActive(isActive);
        this.carOverviewRepository.save(carOverview);
    }

    @Override
    @Transactional
    public void setIsAvailable(Long carId, boolean isAvailable) {
        CarOverview carOverview = this.carOverviewRepository.findCarOverviewByCarId(carId).orElseThrow(
                () -> new EntityNotFoundException("Detalhes do carro não encontrado"));

        carOverview.setAvailable(isAvailable);
        this.carOverviewRepository.save(carOverview);
    }

    @Override
    @Transactional
    public void increaseNumberOfTrips(Long carId){
        CarOverview carOverview = this.carOverviewRepository.findCarOverviewByCarId(carId).orElseThrow(
                () -> new EntityNotFoundException("Detalhes do carro não encontrado"));

        carOverview.setNumberTrips(carOverview.getNumberTrips() + 1);
        this.carOverviewRepository.save(carOverview);
    }
}