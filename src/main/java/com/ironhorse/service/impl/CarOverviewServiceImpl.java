package com.ironhorse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ironhorse.model.CarOverview;
import com.ironhorse.repository.CarOverviewRepository;
import com.ironhorse.service.CarOverviewService;

@Service
public class CarOverviewServiceImpl implements CarOverviewService {

        @Autowired
        private CarOverviewRepository carOverviewRepository;
    
        @Override
        public CarOverview save(CarOverview carOverview) {
            return carOverviewRepository.save(carOverview);
        }
    
        @Override
        public List<CarOverview> findAll() {
            return carOverviewRepository.findAll();
        }
    
        @Override
        public CarOverview findById(Long id) {
            return carOverviewRepository.findById(id).orElseThrow(() -> new RuntimeException("CarOverview not found"));
        }
    
        @Override
        public void deleteById(Long id) {
            carOverviewRepository.deleteById(id);
        }
}
