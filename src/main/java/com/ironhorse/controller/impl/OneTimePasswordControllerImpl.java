package com.ironhorse.controller.impl;

import com.ironhorse.controller.OneTimePasswordController;
import com.ironhorse.dto.OneTimePasswordValidationDto;
import com.ironhorse.service.OneTimePasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/otp")
public class OneTimePasswordControllerImpl implements OneTimePasswordController{
    private final OneTimePasswordService oneTimePasswordService;

    public OneTimePasswordControllerImpl(OneTimePasswordService oneTimePasswordService) {
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/generate/{rentalId}")
    public ResponseEntity<String> generateOneTimePassword(@PathVariable Long rentalId) {
        String oneTimePassword = this.oneTimePasswordService.generateOneTimePassword(rentalId);
        return ResponseEntity.status(HttpStatus.OK).body(oneTimePassword);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateOneTimePassword(@RequestBody OneTimePasswordValidationDto oneTimePasswordValidationDto) {
        boolean validateOneTimePassword = this.oneTimePasswordService.validateOneTimePassword(oneTimePasswordValidationDto.rentalId(),
                oneTimePasswordValidationDto.oneTimePassword());
        return ResponseEntity.status(HttpStatus.OK).body(validateOneTimePassword);
    }
}
