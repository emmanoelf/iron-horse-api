package com.ironhorse.mapper;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
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
}
