package com.ironhorse.repository;

import com.ironhorse.model.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
    Optional<CarInfo> findByCarId(Long carId);
    Long deleteByCarId(Long carId);
}
