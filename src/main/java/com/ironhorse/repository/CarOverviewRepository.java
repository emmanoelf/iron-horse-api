package com.ironhorse.repository;

import com.ironhorse.model.Car;
import com.ironhorse.model.CarOverview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarOverviewRepository extends JpaRepository<CarOverview, Long> {
    Car findCarById();
}
