package com.ironhorse.repository.projection;

import com.ironhorse.model.RentalStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RentalDetailsProjection {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private RentalStatus status;
    private Long carId;
    private String carBrand;
    private String carModel;
    private Long carManufactureYear;
    private BigDecimal price;
    private int daysRented;
    private BigDecimal totalPrice;

    public RentalDetailsProjection(Long id, LocalDateTime startDate, LocalDateTime expectedEndDate, RentalStatus status, Long carId, String carBrand, String carModel, Long carManufactureYear, BigDecimal price, int daysRented) {
        this.id = id;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.status = status;
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carManufactureYear = carManufactureYear;
        this.price = price;
        this.daysRented = daysRented;
    }

    public RentalDetailsProjection(Long id, LocalDateTime startDate, LocalDateTime expectedEndDate, RentalStatus status, Long carId, String carBrand, String carModel, Long carManufactureYear, BigDecimal price, int daysRented, BigDecimal totalPrice) {
        this.id = id;
        this.startDate = startDate;
        this.expectedEndDate = expectedEndDate;
        this.status = status;
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carManufactureYear = carManufactureYear;
        this.price = price;
        this.daysRented = daysRented;
        this.totalPrice = totalPrice;
    }
}