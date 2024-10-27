package com.ironhorse.client;

import com.ironhorse.dto.googleGeoCode.GeocodeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleGeocodeClient", url = "https://maps.googleapis.com/maps/api/geocode")
public interface GoogleGeocodeClient {

    @GetMapping("/json")
    ResponseEntity<GeocodeResponseDto> searchCoordinates(@RequestParam("address") String address,
                                                          @RequestParam("key") String apiKey);
}
