package com.ironhorse.mapper;

import com.ironhorse.dto.*;
import com.ironhorse.model.CarOverview;

public class CarOverviewMapper {
    public static CarOverviewDto toInfoDto(CarOverview carOverview,CarInfoDto carInfoDto){
//        CarDto carDto = null;
//
//        Car car = carOverview.getCar();
//        CarInfoDto carInfoDto = CarInfoMapper.toDto(car.getCarInfo());

        return new CarOverviewDto(
                carOverview.getDescription(),
                carOverview.getNumberTrips(),
                carOverview.isActive(),
                carOverview.isAvailable(),
                carOverview.getPrice(),
                carInfoDto,
                null
        );
    }

    public static CarOverview toModel(CarOverviewCreateDto carOverviewCreateDto){
        return CarOverview.builder()
                .description(carOverviewCreateDto.description())
                .isActive(carOverviewCreateDto.isActive())
                .isAvailable(carOverviewCreateDto.isAvailable())
                .price(carOverviewCreateDto.price())
                .build();
    }

    public static CarOverviewListDto toDtoOverview(CarOverview carOverview){
        CarInfoDto carInfoDto = CarInfoMapper.toDto(carOverview.getCar().getCarInfo());

        CarWithCarInfoDto carWithCarInfoDto = new CarWithCarInfoDto(
                carOverview.getCar().getBrand(),
                carOverview.getCar().getModel(),
                carOverview.getCar().getManufactureYear(),
                carInfoDto
        );

        return new CarOverviewListDto(
                carOverview.getDescription(),
                carOverview.getNumberTrips(),
                carOverview.isActive(),
                carOverview.isAvailable(),
                carOverview.getPrice(),
                carWithCarInfoDto,
                carOverview.getCreated_at(),
                carOverview.getUpdated_at()
        );
    }

    public static CarOverviewResponseDto toDto(CarOverview carOverview){
        return new CarOverviewResponseDto(
                carOverview.getDescription(),
                carOverview.getNumberTrips(),
                carOverview.isActive(),
                carOverview.isAvailable(),
                carOverview.getPrice(),
                carOverview.getCreated_at(),
                carOverview.getUpdated_at()
        );
    }
}
