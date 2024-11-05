package com.ironhorse.repository;

import com.ironhorse.model.Car;
import com.ironhorse.repository.projection.CarResumeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Long deleteCarById(Long id);

    @Query("""
            SELECT NEW com.ironhorse.repository.projection.CarResumeProjection(
                c.id,
                c.brand,
                c.model,
                c.manufactureYear,
                ui.city,
                ui.latitude,
                ui.longitude,
                AVG(r.rate),
                co.numberTrips,
                co.price
            )
            FROM Car c
            INNER JOIN User u ON c.user.id = u.id
            INNER JOIN UserInfo ui ON u.id = ui.id
            INNER JOIN CarOverview co ON c.id = co.id
            INNER JOIN Review r ON c.id = r.car.id
            WHERE co.isAvailable = true AND ui.city = :city
            GROUP BY c.id, c.brand, c.model, ui.city, co.numberTrips, co.price
            """)
    List<CarResumeProjection> findCarResumesByCity(@Param("city") String city);

    @Query("SELECT c FROM Car c " +
            "INNER JOIN CarOverview co ON c.id = co.car.id " +
            "LEFT JOIN Rental r ON c.id = r.car.id " +
            "WHERE c.id = :id " +
            "AND co.isAvailable = true " +
            "AND co.isActive = true " +
            "AND NOT EXISTS (SELECT 1 FROM Rental r2 WHERE r2.car.id = c.id " +
            "                AND ((r2.startDate <= :endDate AND r2.expectedEndDate >= :startDate)))")
    Optional<Car> isCarAvailableWithinDates(@Param("id") Long id,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);
}
