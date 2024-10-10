package com.ironhorse.repository;

import com.ironhorse.dto.CarOverviewCarDto;
import com.ironhorse.model.CarOverview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarOverviewRepository extends JpaRepository<CarOverview, Long> {
Optional<CarOverviewCarDto> getCarOverviewById(Long id);



}
