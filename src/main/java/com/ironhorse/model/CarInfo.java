package com.ironhorse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    private String engineHorsePower;

    @Column(nullable = false)
    private String mileage;

    @Column(nullable = false)
    private String fuelType;

    @Column(nullable = false)
    private String renavam;

    @Column(nullable = false)
    private String insuranceName;

    @Column(nullable = false)
    private boolean insurance;

    @Column(nullable = false)
    private boolean insulfilm;

    @Column(nullable = false)
    private boolean tagPike;

    @Column(nullable = false)
    private boolean antiTheftSecret;

    @Column(nullable = false)
    private boolean multimedia;

    @Column(nullable = false)
    private boolean airConditioner;

    @Column(nullable = false)
    private boolean electricWindowsAndLocks;

    @Column(nullable = false)
    private boolean triangle;

    @Column(nullable = false)
    private boolean monkey;

    @Column(nullable = false)
    private boolean wheelWrench;

    @Column(nullable = false)
    private boolean spareTire;

    @Column(nullable = false)
    private boolean fireExtinguisher;

    @Column(nullable = false)
    private boolean alarm;

    @Column(nullable = false)
    private boolean smokersAccepted;

    @Column(nullable = false)
    private boolean tagActivated;

    @Column(nullable = false)
    private boolean isFinesBelongToTheOffender;

    @Column(nullable = false)
    private boolean isTermsUser;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false, unique = true)
    private Car car;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;


    public void setIsFinesBelongToTheOffender(Boolean finesBelongToTheOffender) {
        this.isFinesBelongToTheOffender = finesBelongToTheOffender;
    }

    public void setIsTermsUser(Boolean termsUser) {
        this.isTermsUser = termsUser;
    }
}