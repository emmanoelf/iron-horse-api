package com.ironhorse.mapper;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.dto.CarUpdateDto;
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
    public static Car toUpdateModel(CarUpdateDto carUpdateDto) {
        return Car.builder()
                .brand(carUpdateDto.brand())
                .model(carUpdateDto.model())
                .manufactureYear(carUpdateDto.manufactureYear())
                .carInfo(CarInfoMapper.toUpdateModel(carUpdateDto.carInfoUpdateDto()))
                .build();
    }

    public static CarUpdateDto carUpdateDto(Car car) {
        return new CarUpdateDto(
                car.getBrand(),
                car.getModel(),
                car.getManufactureYear(),
                CarInfoMapper.toUpdateDto(car.getCarInfo())
        );
    }
}