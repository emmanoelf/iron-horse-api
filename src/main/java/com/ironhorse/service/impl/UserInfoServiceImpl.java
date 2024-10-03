package com.ironhorse.service.impl;

import com.ironhorse.dto.UserInfoDto;
import com.ironhorse.dto.UserInfoResponseDto;
import com.ironhorse.exception.ForbiddenAccessException;
import com.ironhorse.exception.UserInfoNotFoundException;
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

    @Override
    public UserInfoResponseDto findByUserId(Long userId) {
        this.userRepository.findById(userId).orElseThrow(() -> new UserNotFound("Usuário não encontrado"));

        UserInfo userInfo = this.userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new UserInfoNotFoundException("Informações não encontradas"));

        if(!userInfo.getUser().getId().equals(userId)){
            throw new ForbiddenAccessException("Você não pode acessar este recurso");
        }

        return UserInfoMapper.toDto(userInfo);
    }

    @Override
    @Transactional
    public Long deleteByUserId(Long userId) {
        Long affectedRow = this.userInfoRepository.deleteByUserId(userId);

        if(affectedRow == 0){
            throw new UserInfoNotFoundException("Informações do usuário não encontradas");
        }

        this.userInfoRepository.flush();
        return affectedRow;
    }

    @Override
    @Transactional
    public UserInfoResponseDto update(UserInfoDto userInfoDto, Long userId) {
        UserInfo userInfo = this.userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserInfoNotFoundException("Informações do usuário não encontradas"));

        userInfo.setStreetAddress(userInfoDto.cpf());
        userInfo.setStreetAddress(userInfoDto.streetAddress());
        userInfo.setStreetName(userInfoDto.streetName());
        userInfo.setStreetNumber(userInfoDto.streetNumber());
        userInfo.setDistrict(userInfoDto.district());
        userInfo.setZipcode(userInfoDto.zipcode());
        userInfo.setCity(userInfoDto.city());
        userInfo.setState(userInfoDto.state());
        userInfo.setDriverLicense(userInfoDto.driverLicense());

        this.userInfoRepository.flush();
        return UserInfoMapper.toDto(userInfo);
    }
}
