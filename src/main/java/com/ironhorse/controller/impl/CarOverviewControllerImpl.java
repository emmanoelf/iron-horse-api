package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarOverviewController;
import com.ironhorse.dto.*;
import com.ironhorse.mapper.CarOverviewMapper;
import com.ironhorse.service.CarInfoService;
import com.ironhorse.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ironhorse.model.CarOverview;
import com.ironhorse.service.CarOverviewService;

@RestController
@RequestMapping("/v1/car-overviews")
public class CarOverviewControllerImpl implements CarOverviewController {
    private final CarOverviewService carOverviewService;
    private final CarService carService;
    private final CarInfoService carInfoService;

    public CarOverviewControllerImpl(CarOverviewService carOverviewService,
                                     CarInfoService carInfoService, CarService carService) {
        this.carOverviewService = carOverviewService;
        this.carInfoService = carInfoService;
        this.carService = carService;
    }

    //tentanddo
    @GetMapping("/{id}")
    public ResponseEntity<CarOverviewCarDto> getCarOverviewById(@PathVariable Long id) {
        CarOverview carOverview = carOverviewService.findById(id);
        CarResponseDto car = carService.findById(id);
        CarInfoDto carInfoDto = carInfoService.findCarById(id);
        CarOverviewCarDto carOverviewCarDto = CarOverviewMapper.toDto(carOverview, car, carInfoDto);
        return ResponseEntity.ok(carOverviewCarDto);
    }

    @Override
    @PostMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarOverviewResponseDto> createCarOverview(@RequestBody @Valid CarOverviewCreateDto carOverviewCreateDto,
                                                                    @PathVariable Long carId) {
        CarOverviewResponseDto savedCarOverview = this.carOverviewService.save(carOverviewCreateDto, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarOverview);
    }

    @Override
    @PutMapping("/{carId}")
    public ResponseEntity<CarOverviewResponseDto> updateCarOverview(@RequestBody CarOverviewCreateDto carOverviewCreateDto,
                                                                    @PathVariable Long carId) {
        CarOverviewResponseDto carOverviewResponseDto = this.carOverviewService.update(carOverviewCreateDto, carId);
        return ResponseEntity.status(HttpStatus.OK).body(carOverviewResponseDto);
    }
}
