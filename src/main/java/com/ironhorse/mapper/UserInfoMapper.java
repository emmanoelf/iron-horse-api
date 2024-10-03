package com.ironhorse.mapper;

import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.model.UserInfo;

public class UserInfoMapper {

    public static UserInfo toModel(UserInfoDto userInfoDto) {
        return UserInfo.builder()
                .cpf(userInfoDto.cpf())
                .streetAddress(userInfoDto.streetAddress())
                .streetName(userInfoDto.streetName())
                .streetNumber(userInfoDto.streetNumber())
                .district(userInfoDto.district())
                .zipcode(userInfoDto.zipcode())
                .city(userInfoDto.city())
                .state(userInfoDto.state())
                .driverLicense(userInfoDto.driverLicense())
                .build();
    }

    public static UserInfoResponseDto toDto(UserInfo userInfo) {
        return new UserInfoResponseDto(
                userInfo.getCpf(),
                userInfo.getStreetAddress(),
                userInfo.getStreetName(),
                userInfo.getStreetNumber(),
                userInfo.getDistrict(),
                userInfo.getZipcode(),
                userInfo.getCity(),
                userInfo.getState(),
                userInfo.getDriverLicense(),
                userInfo.getCreated_at()
        );
    }
}
