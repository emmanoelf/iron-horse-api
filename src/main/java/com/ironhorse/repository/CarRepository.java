package com.ironhorse.repository;

import com.ironhorse.model.Car;
import com.ironhorse.repository.projection.CarResumeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Long deleteCarById(Long id);

    @Query("""
            SELECT DISTINCT NEW com.ironhorse.repository.projection.CarResumeProjection(
                c.id,
                c.brand,
                c.model,
                c.manufactureYear,
                ui.city,
                ui.state,
                r.rate,
                co.numberTrips,
                co.price
            )
            FROM Car c
            INNER JOIN User u ON c.user.id = u.id
            INNER JOIN UserInfo ui ON u.id = ui.id
            INNER JOIN CarOverview co ON c.id = co.id
            INNER JOIN Review r ON c.id = r.car.id
            WHERE co.isAvailable = true AND ui.city = :city
            """)
    List<CarResumeProjection> findCarResumesByCity(@Param("city") String city);
}
