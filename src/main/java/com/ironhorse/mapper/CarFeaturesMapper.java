package com.ironhorse.mapper;

import com.ironhorse.dto.CarFeaturesDto;
import com.ironhorse.dto.CarFeaturesUpdateDto;
import com.ironhorse.dto.CarInfoConsentsDto;
import com.ironhorse.model.CarFeatures;
import com.ironhorse.model.CarInfo;

public class CarFeaturesMapper {
    public static CarFeatures toModel(CarFeaturesDto carFeaturesDto) {
        return CarFeatures.builder()
//                .isSmokersAccepted(carFeaturesDto.smokersAccepted())
                .tagPike(carFeaturesDto.tagPike())
                .spareTire(carFeaturesDto.spareTire())
                .electricWindowsAndLocks(carFeaturesDto.electricWindowsAndLocks())
                .wheelWrench(carFeaturesDto.wheelWrench())
                .jack(carFeaturesDto.jack())
                .triangle(carFeaturesDto.triangle())
//                .isTagActivated(carFeaturesDto.tagActivated())
                .alarm(carFeaturesDto.alarm())
                .insulfilm(carFeaturesDto.insulfilm())
                .multimedia(carFeaturesDto.multimedia())
                .airConditioner(carFeaturesDto.airConditioner())
                .fireExtinguisher(carFeaturesDto.fireExtinguisher())
                .antiTheftSecret(carFeaturesDto.antiTheftSecret())
//                .isDocsUptoDate(carFeaturesDto.isDocsUptoDate())
//                .isFinesBelongToTheOffender(carFeaturesDto.isFinesBelongToTheOffender())
//                .isVeicleModified(carFeaturesDto.isVeicleModified())
//                .isTrueInformation(carFeaturesDto.isTrueInformation())
                .build();
    }

    public static CarFeaturesDto toDto(CarFeatures carFeatures) {
        return new CarFeaturesDto(
//                carFeatures.isVeicleModified(),
//                carFeatures.isTrueInformation(),
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
                carFeatures.isAlarm()
//                carFeatures.isSmokersAccepted(),
//                carFeatures.isTagActivated(),
//                carFeatures.isFinesBelongToTheOffender(),
//                carFeatures.isDocsUptoDate()
        );

    }

    public static CarFeatures toUpdateModel(CarFeaturesUpdateDto carFeaturesUpdateDto) {
        return CarFeatures.builder()
                .isSmokersAccepted(carFeaturesUpdateDto.smokersAccepted())
                .tagPike(carFeaturesUpdateDto.tagPike())
                .spareTire(carFeaturesUpdateDto.spareTire())
                .electricWindowsAndLocks(carFeaturesUpdateDto.electricWindowsAndLocks())
                .wheelWrench(carFeaturesUpdateDto.wheelWrench())
                .jack(carFeaturesUpdateDto.jack())
                .triangle(carFeaturesUpdateDto.triangle())
                .isTagActivated(carFeaturesUpdateDto.tagActivated())
                .alarm(carFeaturesUpdateDto.alarm())
                .insulfilm(carFeaturesUpdateDto.insulfilm())
                .multimedia(carFeaturesUpdateDto.multimedia())
                .airConditioner(carFeaturesUpdateDto.airConditioner())
                .fireExtinguisher(carFeaturesUpdateDto.fireExtinguisher())
                .antiTheftSecret(carFeaturesUpdateDto.antiTheftSecret())
                .isDocsUptoDate(carFeaturesUpdateDto.isDocsUptoDate())
                .isFinesBelongToTheOffender(carFeaturesUpdateDto.isFinesBelongToTheOffender())
                .isVeicleModified(carFeaturesUpdateDto.isVeicleModified())
                .isTrueInformation(carFeaturesUpdateDto.isTrueInformation())
                .build();
    }

    public static CarFeaturesUpdateDto toUpdateDto(CarFeatures carFeatures) {
        CarFeaturesUpdateDto carFeaturesUpdateDto = new CarFeaturesUpdateDto(
                carFeatures.isVeicleModified(),
                carFeatures.isTrueInformation(),
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
                carFeatures.isDocsUptoDate()
        ); return carFeaturesUpdateDto;

    }

    public static CarFeatures toPartialModel(CarInfoConsentsDto dto) {
        return CarFeatures.builder()
                .isDocsUptoDate(dto.docsUptoDate())
                .isSmokersAccepted(dto.smokersAccepted())
                .isTrueInformation(dto.trueInformation())
                .isVeicleModified(dto.veicleModified())
                .isTagActivated(dto.tagActivated())
                .isFinesBelongToTheOffender(dto.finesBelongToTheOffender())
                .build();
    }

    public static CarInfoConsentsDto toPartialDto(CarFeatures carFeatures) {
        return new CarInfoConsentsDto(
                carFeatures.isVeicleModified(),
                carFeatures.isTrueInformation(),
                carFeatures.isSmokersAccepted(),
                carFeatures.isTagActivated(),
                carFeatures.isFinesBelongToTheOffender(),
                carFeatures.isDocsUptoDate()
        );
    }

}