package com.ironhorse.service;

import com.ironhorse.dto.CarResponseDto;
import com.ironhorse.dto.CarSaveDto;
import com.ironhorse.dto.CarSaveResponseDto;
import com.ironhorse.dto.CarUpdateDto;
import com.ironhorse.repository.projection.CarResumeProjection;
import com.ironhorse.repository.projection.MyCarsProjection;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarService {
    CarSaveResponseDto save(CarSaveDto carDto);
    CarResponseDto findById(Long id);
    Long deleteById(Long id);
    CarUpdateDto update(CarUpdateDto carSaveDto, Long carId);
    Page<CarResumeProjection> findAllCarsByCityAndDateRange(String city, LocalDateTime startDate, LocalDateTime endDate,
                                                            int page, int size);
    Optional<List<MyCarsProjection>> getAllCarsByIdWithRentals();
}
