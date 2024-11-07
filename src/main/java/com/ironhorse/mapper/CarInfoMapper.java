package com.ironhorse.mapper;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.model.CarInfo;

public class CarInfoMapper {

    public static CarInfo toModel(CarInfoDto carInfoDto) {
        return CarInfo.builder()
                .insurance(carInfoDto.insurance())
                .insuranceName(carInfoDto.insuranceName())
                .renavam(carInfoDto.renavam())
                .chassi(carInfoDto.chassi())
                .directionType(carInfoDto.directionType())
                .fuelType(carInfoDto.fuelType())
                .engineNumber(carInfoDto.engineNumber())
                .cylinderDisplacement(carInfoDto.cylinderDisplacement())
                .licensePlate(carInfoDto.licensePlate())
                .mileage(carInfoDto.mileage())
                .transmission(carInfoDto.transmission())
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
                carInfo.getMileage(),
                carInfo.getFuelType(),
                carInfo.getRenavam(),  // Essa linha deve ser corrigida, já está sendo passada acima
                CarFeaturesMapper.toDto(carInfo.getCarFeatures())
        );
    }


}