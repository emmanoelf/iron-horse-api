package com.ironhorse.service.impl;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.mapper.UserMapper;
import com.ironhorse.model.User;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponseDto save(UserDto userDto) {
        User user = UserMapper.toModel(userDto);

        User savedUser = this.userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }
}
