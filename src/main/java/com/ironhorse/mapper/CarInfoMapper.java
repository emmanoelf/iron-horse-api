package com.ironhorse.mapper;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.model.Car;
import com.ironhorse.model.CarInfo;

public class CarInfoMapper {

    public static CarInfo toModelAssociatedCar(CarInfoDto carInfoDto, Car car) {

        CarInfo carInfo = CarInfo.builder()
                .insurance(carInfoDto.insurance())
                .insuranceName(carInfoDto.insuranceName())
                .renavam(carInfoDto.renavam())
                .licensePlate(carInfoDto.licensePlate())
                .transmission(carInfoDto.transmission())
                .directionType(carInfoDto.directionType())
                .chassi(carInfoDto.chassi())
                .engineNumber(carInfoDto.engineNumber())
                .cylinderDisplacement(carInfoDto.cylinderDisplacement())
                .mileage(carInfoDto.mileage())
                .fuelType(carInfoDto.fuelType())
                .carFeatures(CarFeaturesMapper.toModel(carInfoDto.carFeaturesDto()))
                .build();

        carInfo.setCar(car);

        return carInfo;
    }

    public static CarInfo toModel(CarInfoDto carInfoDto) {

        return  CarInfo.builder()
                .insurance(carInfoDto.insurance())
                .insuranceName(carInfoDto.insuranceName())
                .renavam(carInfoDto.renavam())
                .licensePlate(carInfoDto.licensePlate())
                .transmission(carInfoDto.transmission())
                .directionType(carInfoDto.directionType())
                .chassi(carInfoDto.chassi())
                .engineNumber(carInfoDto.engineNumber())
                .cylinderDisplacement(carInfoDto.cylinderDisplacement())
                .mileage(carInfoDto.mileage())
                .fuelType(carInfoDto.fuelType())
                .carFeatures(CarFeaturesMapper.toModel(carInfoDto.carFeaturesDto()))
                .build();
    }



    public static CarInfoDto toDto(CarInfo carInfo) {
        return new CarInfoDto(
                carInfo.isInsurance(),
                carInfo.getInsuranceName(),
                carInfo.getRenavam(),
                carInfo.getLicensePlate(),
                carInfo.getTransmission(),
                carInfo.getDirectionType(),
                carInfo.getChassi(),
                carInfo.getEngineNumber(),
                carInfo.getCylinderDisplacement(),
                carInfo.getMileage(),
                carInfo.getFuelType(),
                CarFeaturesMapper.toDto(carInfo.getCarFeatures())  // CarFeatures
        );
    }

}