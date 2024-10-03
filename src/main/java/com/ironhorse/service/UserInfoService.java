package com.ironhorse.service;

import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;

public interface UserInfoService {
    UserInfoResponseDto save(UserInfoDto userInfoDto, Long userId);
    UserInfoResponseDto findByUserId(Long userId);
}
