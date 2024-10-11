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
            SELECT NEW com.ironhorse.repository.projection.CarResumeProjection(
                c.id,
                c.brand,
                c.model,
                c.manufactureYear,
                ui.city,
                ui.state,
                AVG(r.rate),
                co.numberTrips,
                co.price
            )
            FROM Car c
            JOIN User u ON c.user.id = u.id
            JOIN UserInfo ui ON u.id = ui.id
            JOIN CarOverview co ON c.id = co.id
            JOIN Review r ON c.id = r.car.id
            WHERE co.isAvailable = true AND ui.city = :city
            GROUP BY c.id, c.brand, c.model, ui.city, ui.state, co.numberTrips, co.price
            """)
    List<CarResumeProjection> findCarResumesByCity(@Param("city") String city);
}
