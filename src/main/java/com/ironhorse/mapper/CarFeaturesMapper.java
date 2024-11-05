package com.ironhorse.mapper;


import com.ironhorse.dto.CarFeaturesDto;
import com.ironhorse.model.CarFeatures;

public class CarFeaturesMapper {
    public static CarFeatures toModel(CarFeaturesDto carFeaturesDto) {
        return CarFeatures.builder()
                .smokersAccepted(carFeaturesDto.smokersAccepted())
                .tagPike(carFeaturesDto.tagPike())
                .spareTire(carFeaturesDto.spareTire())
                .electricWindowsAndLocks(carFeaturesDto.electricWindowsAndLocks())
                .wheelWrench(carFeaturesDto.wheelWrench())
                .jack(carFeaturesDto.jack())
                .triangle(carFeaturesDto.triangle())
                .tagActivated(carFeaturesDto.tagActivated())
                .alarm(carFeaturesDto.alarm())
                .insulfilm(carFeaturesDto.insulfilm())
                .multimedia(carFeaturesDto.multimedia())
                .airConditioner(carFeaturesDto.airConditioner())
                .insurance(carFeaturesDto.insurance())
                .insuranceName(carFeaturesDto.insuranceName())
                .fireExtinguisher(carFeaturesDto.fireExtinguisher())
                .antiTheftSecret(carFeaturesDto.antiTheftSecret())
                .isTermsUser(carFeaturesDto.isTermsUser())
                .isFinesBelongToTheOffender(carFeaturesDto.isFinesBelongToTheOffender())
                .build();

    }

    public static CarFeaturesDto toDto(CarFeatures carFeatures) {
        return new CarFeaturesDto(
                carFeatures.getInsuranceName(),
                carFeatures.isInsurance(),
                carFeatures.isInsulfilm(),
                carFeatures.isTagPike(),
                carFeatures.isAntiTheftSecret(),
                carFeatures.isMultimedia(),
                carFeatures.isAirConditioner(),
                carFeatures.isElectricWindowsAndLocks(),
                carFeatures.isTriangle(),
                carFeatures.isJack(),
                carFeatures.isWheelWrench(),
                carFeatures.isSpareTire(),
                carFeatures.isFireExtinguisher(),
                carFeatures.isAlarm(),
                carFeatures.isSmokersAccepted(),
                carFeatures.isTagActivated(),
                carFeatures.isFinesBelongToTheOffender(),
                carFeatures.isTermsUser()
        );

    }
}
