package com.ironhorse.service.impl;

import com.ironhorse.dto.EmailDto;
import com.ironhorse.dto.UserDto;
import com.ironhorse.dto.UserResponseDto;
import com.ironhorse.exception.UserNotFound;
import com.ironhorse.mapper.UserMapper;
import com.ironhorse.model.User;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.service.EmailService;
import com.ironhorse.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    @Override
    public UserResponseDto save(UserDto userDto) {
        User user = UserMapper.toModel(userDto);
        String hashedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        EmailDto email = new EmailDto(user.getEmail(), "Bem vindo!", "email-template-confirmation", null);
        this.emailService.sendEmail(email);
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

        String hashedPassword = this.passwordEncoder.encode(userDto.password());
        User updatedUser = user.get();
        updatedUser.setName(userDto.name());
        updatedUser.setEmail(userDto.email());
        updatedUser.setPassword(hashedPassword);
        updatedUser.setPhone(userDto.phone());

        this.userRepository.save(updatedUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public User findByEmail(String email) {
        User user = this.userRepository.findByEmail(email);

        if(user == null) {
            throw new UserNotFound("Usuário não encontrado");
        }

        return user;
    }
}
