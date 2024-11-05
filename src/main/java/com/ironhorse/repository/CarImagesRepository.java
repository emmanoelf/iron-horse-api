package com.ironhorse.repository;

import com.ironhorse.model.CarImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarImagesRepository extends JpaRepository<CarImages, Long> {
    List<CarImages> findByCarInfoId(Long id);
}
