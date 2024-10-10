package com.ironhorse.mapper;

import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.model.Car;
import com.ironhorse.model.CarOverview;

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

    public static CarOverviewDto toDto(CarOverview carOverview){
        CarDto carDto = null;

        Car car = carOverview.getCar();
        CarInfoDto carInfoDto = CarInfoMapper.toDto(car.getCarInfo());

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
