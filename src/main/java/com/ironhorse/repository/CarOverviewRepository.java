package com.ironhorse.repository;

import com.ironhorse.model.CarOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarOverviewRepository extends JpaRepository<CarOverview, Long> {
    @Query("SELECT co " +
            "FROM CarOverview co " +
            "INNER JOIN co.car c " +
            "INNER JOIN c.carInfo ci " +
            "LEFT JOIN c.reviews r " +
            "WHERE c.id = :carId")
    Optional<CarOverview> getAllDetailsFromCarOverview(@Param("carId") Long carId);

    Optional<CarOverview> findCarOverviewByCarId(Long carId);
}
