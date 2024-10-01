package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarController;
import com.ironhorse.dto.CarDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cars")
public class CarControllerImpl implements CarController {

    private final CarService carService;

    public CarControllerImpl(CarService carService) {
        this.carService = carService;
    }

    @Override
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarResponseDto> save(@RequestBody @Valid CarDto carDto, @PathVariable Long id) {
        CarResponseDto carResponseDto = this.carService.save(carDto, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponseDto);
    }
}
