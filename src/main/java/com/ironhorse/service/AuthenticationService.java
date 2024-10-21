package com.ironhorse.service;

import com.ironhorse.dto.AuthenticationDto;

public interface AuthenticationService {
    String authenticate(AuthenticationDto authenticationDto);
}
