package com.ironhorse.repository;

import com.ironhorse.model.CarFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarFeaturesRepository extends JpaRepository<CarFeatures, Long> {
}
