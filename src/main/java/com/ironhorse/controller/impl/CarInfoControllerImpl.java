package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarInfoController;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.service.CarInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/car_info")
public class CarInfoControllerImpl implements CarInfoController {

    private final CarInfoService carInfoService;

    public CarInfoControllerImpl(CarInfoService carInfoService) {
        this.carInfoService = carInfoService;
    }

    @Override
    @PostMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarInfoDto> save(@RequestBody @Valid  CarInfoDto carInfoDto,@PathVariable Long carId) {
        CarInfoDto carInfo = this.carInfoService.save(carInfoDto, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(carInfo);
    }

    @Override
    @GetMapping("/{carId}")
    public ResponseEntity<CarInfoDto> findCarById(@PathVariable Long carId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.carInfoService.findCarById(carId));
    }

    @Override
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteByCarId(@PathVariable Long carId) {
        this.carInfoService.deleteByCarId(carId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{carId}")
    public ResponseEntity<CarInfoDto> update(@RequestBody @Valid CarInfoDto carInfoDto,
                                                 @PathVariable Long carId) {
        CarInfoDto carInfo = this.carInfoService.update(carInfoDto, carId);
        return ResponseEntity.status(HttpStatus.OK).body(carInfo);
    }
}
