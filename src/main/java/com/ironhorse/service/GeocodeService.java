package com.ironhorse.service;

import com.ironhorse.dto.googleGeoCode.LocationDto;

public interface GeocodeService {
    LocationDto getLatitudeAndLongitude(String address);
}
