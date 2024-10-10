package com.ironhorse.mapper;

import com.ironhorse.dto.*;
import com.ironhorse.model.Car;
import com.ironhorse.model.CarOverview;

import java.util.List;

public class CarOverviewMapper {

    public static CarOverview toModel(CarOverviewDto carOverviewDto){

        CarDto carDto = null;
        Car car = CarMapper.toModel(carDto);

        return CarOverview.builder()
                .description(carOverviewDto.description())
                .isActive(carOverviewDto.isActive())
                .isAvailable(carOverviewDto.isAvailable())
                .price(carOverviewDto.price())
                .car(car)
                .build();
    }

    public static CarOverviewCarDto toDto(CarOverview carOverview, CarResponseDto car, CarInfoDto carInfoDto){

        return new CarOverviewCarDto(
                carOverview.getDescription(),
                carOverview.getNumberTrips(),
                carOverview.isAvailable(),
                carOverview.isActive(),
                carOverview.getPrice(),
                car.model(),
                car.brand(),
                carInfoDto
        );
    };



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
}
