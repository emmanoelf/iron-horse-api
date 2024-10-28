package com.ironhorse.controller.impl;

import com.ironhorse.controller.RentalController;
import com.ironhorse.dto.RentalDto;
import com.ironhorse.dto.RentalResponseDto;
import com.ironhorse.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rentals")
public class RentalControllerImpl implements RentalController {
    private final RentalService rentalService;

    public RentalControllerImpl(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Override
    @PostMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RentalResponseDto> save(@RequestBody RentalDto rentalDto, @PathVariable Long carId) {
        RentalResponseDto rentalResponseDto = this.rentalService.save(rentalDto, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalResponseDto);
    }
}
