package com.ironhorse.controller.impl;

import com.ironhorse.controller.RecoveryPasswordController;
import com.ironhorse.dto.EmailRecoveryDto;
import com.ironhorse.dto.EmailResetPassword;
import com.ironhorse.service.RecoveryPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/recovery")
public class RecoveryPasswordControllerImpl implements RecoveryPasswordController {

    private final RecoveryPasswordService recoveryPasswordService;

    public RecoveryPasswordControllerImpl(RecoveryPasswordService recoveryPasswordService) {
        this.recoveryPasswordService = recoveryPasswordService;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    public ResponseEntity<Void> createLinkRecovery(@RequestBody EmailRecoveryDto email) {
        this.recoveryPasswordService.passwordRecovery(email.email());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestBody EmailResetPassword reset) {
        this.recoveryPasswordService.resetPassword(token, reset.password());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
