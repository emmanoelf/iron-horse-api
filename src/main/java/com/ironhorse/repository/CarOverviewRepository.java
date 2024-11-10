package com.ironhorse.repository;

import com.ironhorse.model.CarOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarOverviewRepository extends JpaRepository<CarOverview, Long> {
    @Query("SELECT co " +
            "FROM CarOverview co " +
            "JOIN FETCH co.car c " +
            "JOIN FETCH c.carInfo ci " +
            "JOIN FETCH c.reviews r " +
            "WHERE c.id = :carId")
    Optional<CarOverview> getAllDetailsFromCarOverview(@Param("carId") Long carId);

    Optional<CarOverview> findCarOverviewByCarId(Long carId);
}
