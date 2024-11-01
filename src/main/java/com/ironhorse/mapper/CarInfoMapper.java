package com.ironhorse.mapper;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.model.CarInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarInfoMapper {

    public static CarInfo toModel(CarInfoDto carInfoDto) {
        return CarInfo.builder()
                .renavam(carInfoDto.renavam())
                .chassi(carInfoDto.chassi())
                .directionType(carInfoDto.directionType())
                .fuelType(carInfoDto.fuelType())
                .engineNumber(carInfoDto.engineNumber())
                .cylinderDisplacement(carInfoDto.cylinderDisplacement())
                .licensePlate(carInfoDto.licensePlate())
                .mileage(carInfoDto.mileage())
                .transmission(carInfoDto.transmission())
                .build();
    }

    public static CarInfoDto toDto(CarInfo carInfo) {
        return new CarInfoDto(
                carInfo.getRenavam(),
                carInfo.getLicensePlate(),
                carInfo.getTransmission(),
                carInfo.getDirectionType(),
                carInfo.getChassi(),
                carInfo.getEngineNumber(),
                carInfo.getMileage(),
                carInfo.getFuelType(),
                carInfo.getRenavam()
        );
    }
}