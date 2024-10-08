package com.ironhorse.repository;

import com.ironhorse.model.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
    Long deleteCarInfoById(Long id);
}
