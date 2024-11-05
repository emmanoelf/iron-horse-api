package com.ironhorse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String transmission;

    @Column(nullable = false)
    private String directionType;

    @Column(nullable = false)
    private String chassi;

    @Column(nullable = false)
    private String engineNumber;

    @Column(nullable = false)
    private String cylinderDisplacement;

    @Column(nullable = false)
    private String mileage;

    @Column(nullable = false)
    private String fuelType;

    @Column(nullable = false)
    private String renavam;


    @OneToMany(mappedBy = "carInfo",cascade = CascadeType.ALL)
    private List<CarImages> carImages;

    @OneToOne(mappedBy = "carInfo")
    private CarFeatures carFeatures;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

}