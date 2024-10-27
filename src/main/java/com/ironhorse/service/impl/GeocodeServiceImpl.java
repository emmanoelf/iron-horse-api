package com.ironhorse.service.impl;

import com.ironhorse.client.GoogleGeocodeClient;
import com.ironhorse.dto.googleGeoCode.GeocodeResponseDto;
import com.ironhorse.dto.googleGeoCode.LocationDto;
import com.ironhorse.service.GeocodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeocodeServiceImpl implements GeocodeService {
    private final GoogleGeocodeClient googleGeocodeClient;

    @Value("${api.key.google-maps}")
    private final String apiKey;

    public GeocodeServiceImpl(GoogleGeocodeClient googleGeocodeClient, String apiKey) {
        this.googleGeocodeClient = googleGeocodeClient;
        this.apiKey = apiKey;
    }

    @Override
    public LocationDto getLatitudeAndLongitude(String address) {
        GeocodeResponseDto geocodeResponseDto = this.googleGeocodeClient.searchCoordinates(address, apiKey).getBody();
        if(geocodeResponseDto == null || geocodeResponseDto.results().isEmpty()){
            throw new RuntimeException("Não foi possível obter os dados");
        }

        return geocodeResponseDto.results().get(0).geometry().location();
    }
}
