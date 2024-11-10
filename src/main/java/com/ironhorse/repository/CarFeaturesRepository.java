package com.ironhorse.repository;

import com.ironhorse.model.CarFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarFeaturesRepository extends JpaRepository<CarFeatures, Long> {
    Optional<CarFeatures> findByCarInfoId(Long carInfoId);
}
