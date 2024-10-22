package com.ironhorse.service.impl;

import com.ironhorse.dto.AuthenticationDto;
import com.ironhorse.dto.TokenDto;
import com.ironhorse.model.User;
import com.ironhorse.model.UserRole;
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
    public TokenDto authenticate(AuthenticationDto authenticationDto) {
        User user = this.userRepository.findByEmail(authenticationDto.email());

        if(user == null){
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        if(!passwordEncoder.matches(authenticationDto.password(), user.getPassword())){
            throw new BadCredentialsException("Login ou senha inválidos");
        }

        String tokenAccess = this.jwtTokenProvider.generateToken(user.getEmail(), user.getId(), user.getRole(), new HashMap<>());
        String refreshToken = this.jwtTokenProvider.generateRefreshTokenFromAccess(tokenAccess);
        return new TokenDto(tokenAccess, refreshToken);
    }

    @Override
    public TokenDto refreshTokenAccess(String refreshToken) {
        if(jwtTokenProvider.isTokenExpired(refreshToken)){
            throw new BadCredentialsException("Refresh token expirado");
        }

        String email = this.jwtTokenProvider.extractEmail(refreshToken);
        Long userId = this.jwtTokenProvider.extractUserId(refreshToken);
        UserRole role = this.jwtTokenProvider.extractRole(refreshToken);

        String tokenAccess = this.jwtTokenProvider.generateToken(email, userId, role, new HashMap<>());
        String newRefreshToken = this.jwtTokenProvider.generateRefreshTokenFromAccess(tokenAccess);
        return new TokenDto(tokenAccess, newRefreshToken);
    }
}
