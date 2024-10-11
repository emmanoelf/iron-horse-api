package com.ironhorse.service.impl;

import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.repository.CarInfoRepository;
import com.ironhorse.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ironhorse.model.CarOverview;
import com.ironhorse.repository.CarOverviewRepository;
import com.ironhorse.service.CarOverviewService;

import java.util.List;

@Service
public class CarOverviewServiceImpl implements CarOverviewService {

    @Autowired
    private CarOverviewRepository carOverviewRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarInfoRepository carInfoRepository;

    @Override
    public CarOverview save(CarOverview carOverview) {
        return carOverviewRepository.save(carOverview);
    }

    @Override
    public List<CarOverviewDto> findAll() {
        List<CarOverviewDto> car = null;
        return car;
    }

    @Override
    public CarOverview findById(Long id) {
        return carOverviewRepository.findById(id).orElseThrow(() -> new RuntimeException("CarOverview not found"));
    }

    @Override
    public void deleteById(Long id) {
        carOverviewRepository.deleteById(id);
    }

    @Override
    public CarOverview getAllDetails(Long carId){
        CarOverview details = this.carOverviewRepository.findCarOverviewByCarId(carId);
        return details;
    }
}