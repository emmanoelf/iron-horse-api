package com.ironhorse.service;

import com.ironhorse.dto.AuthenticationDto;
import com.ironhorse.dto.TokenDto;

public interface AuthenticationService {
    TokenDto authenticate(AuthenticationDto authenticationDto);
    TokenDto refreshTokenAccess(String token);
}
