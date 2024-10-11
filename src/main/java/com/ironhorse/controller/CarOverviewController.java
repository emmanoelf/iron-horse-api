package com.ironhorse.controller;

import org.springframework.http.ResponseEntity;
import com.ironhorse.model.CarOverview;

public interface CarOverviewController {
        ResponseEntity<CarOverview> getAllCarOverviews();

}