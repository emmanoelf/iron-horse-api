package com.ironhorse.mapper;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.model.Car;

public class CarMapper {

    public static Car toModel(CarDto carDto) {
        return Car.builder()
                .brand(carDto.brand())
                .model(carDto.model())
                .manufactureYear(carDto.manufactureYear())
                .build();
    }

    public static CarResponseDto toDto(Car car) {
        return new CarResponseDto(
                car.getBrand(),
                car.getModel(),
                car.getManufactureYear(),
                car.getCreated_at()
        );
    }

    public static Car toSaveModel(CarSaveDto carSaveDto) {
        return Car.builder()
                .brand(carSaveDto.brand())
                .model(carSaveDto.model())
                .manufactureYear(carSaveDto.manufactureYear())
                .carInfo(CarInfoMapper.toModel(carSaveDto.carInfoDto()))
                .build();
    }

    public static CarSaveDto toSaveDto(Car car) {
        return new CarSaveDto(
                car.getBrand(),
                car.getModel(),
                car.getManufactureYear(),
                CarInfoMapper.toDto(car.getCarInfo())
        );
    }
}