package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarOverviewController;
import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.service.CarOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/car-overviews")
public abstract class CarOverviewControllerImpl implements CarOverviewController {

    private final CarOverviewService carOverviewService;

    @Autowired
    public CarOverviewControllerImpl(CarOverviewService carOverviewService) {
        this.carOverviewService = carOverviewService;
    }

    @PostMapping
    public ResponseEntity<CarOverviewDto> save(@RequestBody CarOverviewDto carOverviewDto) {
        CarOverviewDto createdCarOverview = carOverviewService.createCarOverview(carOverviewDto);
        return new ResponseEntity<>(createdCarOverview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarOverviewDto>> getAllCarOverviews() {
        List<CarOverviewDto> carOverviews = carOverviewService.getAllCarOverviews();
        return new ResponseEntity<>(carOverviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarOverviewDto> getCarOverviewById(@PathVariable Long id) {
        return carOverviewService.getCarOverviewById(id)
                .map(carOverviewDto -> new ResponseEntity<>(carOverviewDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarOverviewDto> updateCarOverview(@PathVariable Long id, @RequestBody CarOverviewDto carOverviewDto) {
        CarOverviewDto updatedCarOverview = carOverviewService.updateCarOverview(id, carOverviewDto);
        return new ResponseEntity<>(updatedCarOverview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarOverview(@PathVariable Long id) {
        carOverviewService.deleteCarOverview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
