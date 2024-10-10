package com.ironhorse.controller;

import com.ironhorse.dto.CarOverviewCarDto;
import org.springframework.http.ResponseEntity;

import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.model.CarOverview;

public interface CarOverviewController {
        ResponseEntity<CarOverviewCarDto> getAllCarOverviews();

}