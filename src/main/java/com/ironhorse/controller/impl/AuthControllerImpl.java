package com.ironhorse.controller.impl;

import com.ironhorse.controller.AuthController;
import com.ironhorse.dto.AuthenticationDto;
import com.ironhorse.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;

    public AuthControllerImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto authenticationDto) {
        String token = this.authenticationService.authenticate(authenticationDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
