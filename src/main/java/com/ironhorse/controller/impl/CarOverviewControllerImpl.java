package com.ironhorse.controller.impl;

import java.util.List; // Certifique-se de que este é java.util.List

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.dto.CarOverviewCarDto;
import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.mapper.CarOverviewMapper;
import com.ironhorse.service.CarInfoService;
import com.ironhorse.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ironhorse.model.CarOverview;
import com.ironhorse.service.CarOverviewService;

@RestController
@RequestMapping("/v1/car-overviews")
public class CarOverviewControllerImpl {

    @Autowired
    private CarOverviewService carOverviewService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarInfoService carInfoService;

    @GetMapping
    public ResponseEntity<List<CarOverviewDto>> getAllCarOverviews() {
        List<CarOverviewDto> carOverviews = carOverviewService.findAll();
        return ResponseEntity.ok(carOverviews);
    }

    //tentanddo
    @GetMapping("/{id}")
    public ResponseEntity<CarOverviewCarDto> getCarOverviewById(@PathVariable Long id) {
        CarOverview carOverview = carOverviewService.findById(id);
        CarResponseDto car = carService.findById(id);
        CarInfoDto carInfoDto = carInfoService.findCarById(id);
        CarOverviewCarDto carOverviewCarDto = CarOverviewMapper.toDto(carOverview,car,carInfoDto);
        return ResponseEntity.ok(carOverviewCarDto);
    }


        @PostMapping
    public ResponseEntity<CarOverview> createCarOverview(@RequestBody CarOverview carOverview) {
        CarOverview savedCarOverview = carOverviewService.save(carOverview);
        return ResponseEntity.ok(savedCarOverview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarOverview(@PathVariable Long id) {
        carOverviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
