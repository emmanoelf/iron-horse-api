package com.ironhorse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private boolean jack;

    @Column(nullable = false)
    private boolean wheelWrench;

    @Column(nullable = false)
    private boolean spareTire;

    @Column(nullable = false)
    private boolean fireExtinguisher;

    @Column(nullable = false)
    private boolean alarm;

    @Column(nullable = false)
    private boolean isDocsUptoDate;

    @Column(nullable = false)
    private boolean isSmokersAccepted;

    @Column(nullable = false)
    private boolean isTagActivated;

    @Column(nullable = false)
    private boolean isFinesBelongToTheOffender;

    @Column(nullable = false)
    private boolean isVeicleModified;

    @Column(nullable = false)
    private boolean isTrueInformation;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_info_id", referencedColumnName = "id")
    private CarInfo carInfo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
}
