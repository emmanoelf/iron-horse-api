package com.ironhorse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private String transmission;
    private String directionType;
    private String chassi;
    private String engineNumber;
    private String engineHorsePower;
    private String mileage;
    private String fuelType;
    private String renavam;
    private String insuranceName;
    private Boolean insurance;
    private Boolean insulfilm;
    private Boolean tagPike;
    private Boolean antiTheftSecret;
    private Boolean multimedia;
    private Boolean airConditioner;
    private Boolean electricWindowsAndLocks;
    private Boolean triangle;
    private Boolean monkey;
    private Boolean wheelWrench;
    private Boolean spareTire;
    private Boolean fireExtinguisher;
    private Boolean alarm;
    private Boolean smokersAccepted;
    private Boolean tagActivated;
    private Boolean isFinesBelongToTheOffender;
    private Boolean isTermsUser;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

}