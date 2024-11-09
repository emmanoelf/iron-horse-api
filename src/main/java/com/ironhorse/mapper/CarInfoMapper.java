package com.ironhorse.mapper;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarInfoUpdateDto;
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

    public static CarInfo toUpdateModel(CarInfoUpdateDto carInfoUpdateDto) {

        return  CarInfo.builder()
                .insurance(carInfoUpdateDto.insurance())
                .insuranceName(carInfoUpdateDto.insuranceName())
                .renavam(carInfoUpdateDto.renavam())
                .licensePlate(carInfoUpdateDto.licensePlate())
                .transmission(carInfoUpdateDto.transmission())
                .directionType(carInfoUpdateDto.directionType())
                .chassi(carInfoUpdateDto.chassi())
                .engineNumber(carInfoUpdateDto.engineNumber())
                .cylinderDisplacement(carInfoUpdateDto.cylinderDisplacement())
                .mileage(carInfoUpdateDto.mileage())
                .fuelType(carInfoUpdateDto.fuelType())
                .carFeatures(CarFeaturesMapper.toUpdateModel(carInfoUpdateDto.carFeaturesUpdateDto()))
                .build();
    }



    public static CarInfoUpdateDto toUpdateDto(CarInfo carInfo) {
        return new CarInfoUpdateDto(
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
                CarFeaturesMapper.toUpdateDto(carInfo.getCarFeatures())  // CarFeatures
        );
    }


}