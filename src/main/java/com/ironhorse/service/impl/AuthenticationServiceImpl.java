package com.ironhorse.service.impl;

import com.ironhorse.dto.AuthenticationDto;
import com.ironhorse.model.User;
import com.ironhorse.repository.UserRepository;
import com.ironhorse.security.JwtTokenProvider;
import com.ironhorse.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String authenticate(AuthenticationDto authenticationDto) {
        User user = this.userRepository.findByEmail(authenticationDto.email());

        if(user == null){
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        if(!passwordEncoder.matches(authenticationDto.password(), user.getPassword())){
            throw new BadCredentialsException("Login ou senha inválidos");
        }

        return this.jwtTokenProvider.generateToken(user.getEmail(), user.getId(), user.getRole(), new HashMap<>());
    }
}
