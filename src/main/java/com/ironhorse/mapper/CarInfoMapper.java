package com.ironhorse.mapper;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.model.CarInfo;

public class CarInfoMapper {

    public static CarInfo toModel(CarInfoDto carInfoDto) {
        return CarInfo.builder()
                .airConditioner(carInfoDto.airConditioner())
                .insurance(carInfoDto.insurance())
                .alarm(carInfoDto.alarm())
                .antiTheftSecret(carInfoDto.antiTheftSecret())
                .chassi(carInfoDto.chassi())
                .directionType(carInfoDto.directionType())
                .fuelType(carInfoDto.fuelType())
                .engineNumber(carInfoDto.engineNumber())
                .electricWindowsAndLocks(carInfoDto.electricWindowsAndLocks())
                .engineHorsePower(carInfoDto.engineHorsePower())
                .insulfilm(carInfoDto.insulfilm())
                .insuranceName(carInfoDto.insuranceName())
                .insurance(carInfoDto.insurance())
                .fireExtinguisher(carInfoDto.fireExtinguisher())
                .licensePlate(carInfoDto.licensePlate())
                .monkey(carInfoDto.monkey())
                .mileage(carInfoDto.mileage())
                .multimedia(carInfoDto.multimedia())
                .smokersAccepted(carInfoDto.smokersAccepted())
                .spareTire(carInfoDto.spareTire())
                .renavam(carInfoDto.renavam())
                .tagPike(carInfoDto.tagPike())
                .tagActivated(carInfoDto.tagActivated())
                .transmission(carInfoDto.transmission())
                .triangle(carInfoDto.triangle())
                .wheelWrench(carInfoDto.wheelWrench())
                .isFinesBelongToTheOffender(carInfoDto.isFinesBelongToTheOffender())
                .isTermsUser(carInfoDto.isTermsUser())
                .build();
    }

    public static CarInfoDto toDto(CarInfo carInfo) {
        return new CarInfoDto(
                carInfo.getLicensePlate(),
                carInfo.getTransmission(),
                carInfo.getDirectionType(),
                carInfo.getChassi(),
                carInfo.getEngineNumber(),
                carInfo.getEngineHorsePower(),
                carInfo.getMileage(),
                carInfo.getFuelType(),
                carInfo.getRenavam(),
                carInfo.getInsuranceName(),
                carInfo.isInsurance(),
                carInfo.isInsulfilm(),
                carInfo.isTagPike(),
                carInfo.isAntiTheftSecret(),
                carInfo.isMultimedia(),
                carInfo.isAirConditioner(),
                carInfo.isElectricWindowsAndLocks(),
                carInfo.isTriangle(),
                carInfo.isMonkey(),
                carInfo.isWheelWrench(),
                carInfo.isSpareTire(),
                carInfo.isFireExtinguisher(),
                carInfo.isAlarm(),
                carInfo.isSmokersAccepted(),
                carInfo.isTagActivated(),
                carInfo.isFinesBelongToTheOffender(),
                carInfo.isTermsUser());
    }
}