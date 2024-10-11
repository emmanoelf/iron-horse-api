package com.ironhorse.repository;

import com.ironhorse.model.CarOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarOverviewRepository extends JpaRepository<CarOverview, Long> {
    @Query("SELECT co FROM CarOverview co JOIN FETCH co.car c JOIN FETCH c.carInfo ci WHERE c.id = :carId")
    CarOverview findCarOverviewByCarId(@Param("carId") Long carId);
}
