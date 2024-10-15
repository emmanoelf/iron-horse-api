package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarOverviewController;
import com.ironhorse.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ironhorse.service.CarOverviewService;

@RestController
@RequestMapping("/v1/car-overviews")
public class CarOverviewControllerImpl implements CarOverviewController {
    private final CarOverviewService carOverviewService;

    public CarOverviewControllerImpl(CarOverviewService carOverviewService) {
        this.carOverviewService = carOverviewService;
    }

    @GetMapping("/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarOverviewListDto> getCarOverviewByCarId(@PathVariable Long carId) {
        CarOverviewListDto carOverview = this.carOverviewService.findByCarIdWithDetails(carId);
        return ResponseEntity.status(HttpStatus.OK).body(carOverview);
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarOverviewResponseDto> updateCarOverview(@RequestBody CarOverviewCreateDto carOverviewCreateDto,
                                                                    @PathVariable Long carId) {
        CarOverviewResponseDto carOverviewResponseDto = this.carOverviewService.update(carOverviewCreateDto, carId);
        return ResponseEntity.status(HttpStatus.OK).body(carOverviewResponseDto);
    }
}
