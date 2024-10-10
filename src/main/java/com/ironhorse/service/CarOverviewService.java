package com.ironhorse.service;

import com.ironhorse.model.CarOverview;
import java.util.List;

public interface CarOverviewService {
    CarOverview save(CarOverview carOverview);
    List<CarOverview> findAll();
    CarOverview findById(Long id);
    void deleteById(Long id);
}