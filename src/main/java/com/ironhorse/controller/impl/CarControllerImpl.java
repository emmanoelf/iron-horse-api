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

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.carService.findById(id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.carService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{carId}/{userId}")
    public ResponseEntity<CarResponseDto> update(@RequestBody @Valid CarDto carDto,
                                                 @PathVariable Long carId,
                                                 @PathVariable Long userId) {
        CarResponseDto carResponseDto = this.carService.update(carDto, carId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(carResponseDto);
    }
}
