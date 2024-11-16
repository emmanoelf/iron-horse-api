package com.ironhorse.mapper;

import com.ironhorse.dto.*;
import com.ironhorse.model.Car;

public class CarMapper {

    public static Car toModel(CarDto carDto) {
        return Car.builder()
                .brand(carDto.brand())
                .model(carDto.model())
                .manufactureYear(carDto.manufactureYear())
                .build();
    }

    public static Car toModelWithNoCarInfos(CarSaveDto carSaveDtoDtoDto) {
        return Car.builder()
                .brand(carSaveDtoDtoDto.brand())
                .model(carSaveDtoDtoDto.model())
                .manufactureYear(carSaveDtoDtoDto.manufactureYear())
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

    public static CarUpdateDto carUpdateDto(Car car) {
        return new CarUpdateDto(
                car.getBrand(),
                car.getModel(),
                car.getManufactureYear(),
                CarInfoMapper.toUpdateDto(car.getCarInfo())
        );
    }

    public static CarSaveResponseDto toSaveResponseDto(Car car){
        return new CarSaveResponseDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getManufactureYear(),
                CarInfoMapper.toDto(car.getCarInfo())
        );
    }
}