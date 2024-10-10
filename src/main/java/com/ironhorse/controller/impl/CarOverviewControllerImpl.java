package com.ironhorse.controller.impl;

import java.util.List; // Certifique-se de que este é java.util.List

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ironhorse.model.CarOverview;
import com.ironhorse.service.CarOverviewService;

@RestController
@RequestMapping("/v1/car-overviews")
public class CarOverviewControllerImpl {

    @Autowired
    private CarOverviewService carOverviewService;

    @GetMapping
    public ResponseEntity<List<CarOverview>> getAllCarOverviews() {
        List<CarOverview> carOverviews = carOverviewService.findAll();
        return ResponseEntity.ok(carOverviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarOverview> getCarOverviewById(@PathVariable Long id) {
        CarOverview carOverview = carOverviewService.findById(id);
        return ResponseEntity.ok(carOverview);
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
