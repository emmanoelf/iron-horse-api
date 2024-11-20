package com.ironhorse.repository;

import com.ironhorse.model.Car;
import com.ironhorse.repository.projection.CarResumeProjection;
import com.ironhorse.repository.projection.MyCarsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    co.price,
                    (SELECT ci.path FROM CarImages ci WHERE ci.carInfo.car.id = c.id ORDER BY ci.id ASC LIMIT 1) AS path
                )
                FROM Car c
                LEFT JOIN User u ON c.user.id = u.id
                LEFT JOIN UserInfo ui ON u.id = ui.id
                LEFT JOIN CarOverview co ON c.id = co.id
                LEFT JOIN Review r ON c.id = r.car.id
                LEFT JOIN Rental rt ON rt.car.id = c.id
                WHERE co.isAvailable = true
                AND ui.city = :city
                AND NOT EXISTS (
                        SELECT 1 FROM Rental r
                        WHERE r.car.id = c.id
                        AND (
                            (r.startDate < :startDate AND r.expectedEndDate > :startDate)
                            OR
                            (r.startDate < :endDate AND r.expectedEndDate > :endDate)
                        )
                    )
                GROUP BY c.id, c.brand, c.model, ui.city, co.numberTrips, co.price
            """)
    Page<CarResumeProjection> findAllCarsByCityAndDateRange(
            @Param("city") String city,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

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

    @Query("""
        SELECT NEW com.ironhorse.repository.projection.MyCarsProjection(
            c.id,
            c.brand,
            c.model,
            c.manufactureYear,
            ui.city,
            COALESCE(AVG(r.rate), 0),
            co.numberTrips,
            co.price,
            (SELECT ci.path FROM CarImages ci WHERE ci.carInfo.car.id = c.id ORDER BY ci.id ASC LIMIT 1) AS path,
            rt.id,
            rt.status
        )
        FROM Car c
        LEFT JOIN User u ON c.user.id = u.id
        LEFT JOIN UserInfo ui ON u.id = ui.id
        LEFT JOIN CarOverview co ON c.id = co.car.id
        LEFT JOIN Review r ON c.id = r.car.id
        LEFT JOIN Rental rt ON rt.car.id = c.id
        WHERE c.user.id = :id
        GROUP BY c.id, c.brand, c.model, c.manufactureYear, ui.city, co.numberTrips, co.price, rt.id, rt.status
    """)
    Optional<List<MyCarsProjection>> getCarByUserId(@Param("id") Long id);

}