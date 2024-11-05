package com.ironhorse.controller.impl;

import com.ironhorse.controller.CarInfoController;

import com.ironhorse.dto.CarInfoDto;
import com.ironhorse.service.CarInfoService;
import com.ironhorse.service.impl.FileLocalStorageServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("v1/car_info")
public class CarInfoControllerImpl implements CarInfoController {

    private final CarInfoService carInfoService;
    private final FileLocalStorageServiceImpl fileLocalStorageService;

    public CarInfoControllerImpl(CarInfoService carInfoService,FileLocalStorageServiceImpl fileLocalStorageService) {
        this.carInfoService = carInfoService;
        this.fileLocalStorageService = fileLocalStorageService;
    }

    @Override
    @PostMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarInfoDto> save(@RequestBody @Valid  CarInfoDto carInfoDto,@PathVariable Long carId) {
        CarInfoDto carInfo = this.carInfoService.save(carInfoDto, carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(carInfo);
    }

    @PostMapping(value ="/image/{id}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> saveCarImages(@RequestBody List<MultipartFile> files,@PathVariable Long id) {
        this.fileLocalStorageService.uploadCarImagesFiles(files, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
