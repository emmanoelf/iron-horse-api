package com.ironhorse.mapper;

import com.ironhorse.model.CarOverview;
import com.ironhorse.dto.CarOverviewDto;
import com.ironhorse.model.Car;
import com.ironhorse.dto.CarDto;

public class CarOverviewMapper {

    // Converte CarOverview para CarOverviewDto
    public CarOverviewDto toDto(CarOverview carOverview) {
        if (carOverview == null) {
            return null;
        }

        CarDto carDto = null;
        Car car = carOverview.getCar();
        if (car != null) {
            carDto = new CarDto(car.getId(), car.getModel(), car.getManufactureYear()); // Supondo que Car tenha esses campos
        }

        return new CarOverviewDto(
                carOverview.getModel(),
                carOverview.getBrand(),
                carOverview.getDescription(),
                carOverview.getNumberTrips(),
                carOverview.isActive(),
                carOverview.isAvailable(),
                carOverview.getYear(),
                carOverview.getPrice(),
                carOverview.getCreated_at(),
                carOverview.getUpdated_at(),
                carDto
        );
    }

    // Converte CarOverviewDto para CarOverview
    public CarOverview toEntity(CarOverviewDto carOverviewDto) {
        if (carOverviewDto == null) {
            return null;
        }

        Car car = null;
        CarDto carDto = carOverviewDto.car();
        if (carDto != null) {
            car = new Car(carDto.id(), carDto.licensePlate(), carDto.color()); // Supondo que Car tenha esses campos
        }

        return new CarOverview(
                null, // ID é gerado automaticamente
                carOverviewDto.model(),
                carOverviewDto.brand(),
                carOverviewDto.description(),
                carOverviewDto.numberTrips(),
                carOverviewDto.isActive(),
                carOverviewDto.isAvailable(),
                carOverviewDto.year(),
                carOverviewDto.price(),
                carOverviewDto.created_at(),
                carOverviewDto.updated_at(),
                car // Define a referência ao Car
        );
    }
}
