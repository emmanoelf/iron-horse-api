package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarController;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.dto.CarSaveResponseDto;
import com.ironhorse.dto.CarUpdateDto;
import com.ironhorse.repository.projection.CarResumeProjection;
import com.ironhorse.service.CarService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarSaveResponseDto> save(@RequestBody @Valid CarSaveDto carSaveDto) {
        CarSaveResponseDto carSaved = this.carService.save(carSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carSaved);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.carService.findById(id));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.carService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{carId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarUpdateDto> update(@RequestBody @Valid CarUpdateDto carUpdateDto,
                                                 @PathVariable Long carId) {
        CarUpdateDto carResponseDto = this.carService.update(carUpdateDto, carId);
        return ResponseEntity.status(HttpStatus.OK).body(carResponseDto);
    }

    @Override
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<CarResumeProjection> findAllCarsByCity(@RequestParam String city,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        Page<CarResumeProjection> projection = this.carService.findAllCarsByCity(city, page, size);
        return projection;
    }
}
