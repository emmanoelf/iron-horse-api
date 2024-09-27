package com.ironhorse.service.impl;

import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.UserMapper;
import com.ironhorse.model.User;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public UserResponseDto findById(Long id) {
        return this.userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(
                        () -> new UserNotFound("Usuário não encontrado"));
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        Long rowsAffected = this.userRepository.deleteUserById(id);

        if(rowsAffected == 0) {
            throw new UserNotFound("Entidade não encontrada");
        }

        this.userRepository.flush();
        return rowsAffected;
    }

    @Override
    public UserResponseDto update(Long id, UserDto userDto) {
        Optional<User> user = this.userRepository.findById(id);

        if(!user.isPresent()) {
            throw new UserNotFound("Usuário não encontrado");
        }

        User updatedUser = user.get();
        updatedUser.setName(userDto.name());
        updatedUser.setEmail(userDto.email());
        updatedUser.setPassword(userDto.password());
        updatedUser.setPhone(userDto.phone());

        this.userRepository.save(updatedUser);
        return UserMapper.toDto(updatedUser);
    }
}
