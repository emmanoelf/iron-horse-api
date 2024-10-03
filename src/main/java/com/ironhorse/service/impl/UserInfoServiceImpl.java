package com.ironhorse.service.impl;

import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.UserInfoMapper;
import com.ironhorse.model.User;
import com.ironhorse.model.UserInfo;
import com.ironhorse.repository.UserInfoRepository;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserInfoResponseDto save(UserInfoDto userInfoDto, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFound("Usuário não encontrado"));

        UserInfo userInfo = UserInfoMapper.toModel(userInfoDto);
        userInfo.setUser(user);
        userInfo = this.userInfoRepository.save(userInfo);

        return UserInfoMapper.toDto(userInfo);
    }
}
