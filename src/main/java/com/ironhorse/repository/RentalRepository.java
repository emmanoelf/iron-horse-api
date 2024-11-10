package com.ironhorse.repository;

import com.ironhorse.model.Rental;
import com.ironhorse.model.RentalStatus;
import com.ironhorse.repository.projection.RentalDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("""
            SELECT NEW com.ironhorse.repository.projection.RentalDetailsProjection(
                r.id,
                r.startDate,
                r.expectedEndDate,
                r.status,
                c.id,
                c.brand,
                c.model,
                c.manufactureYear,
                co.price,
                DATEDIFF(r.expectedEndDate, r.startDate)
            )
                FROM Rental r
                        INNER JOIN Car c ON r.car.id = c.id
                        INNER JOIN CarOverview co ON r.car.id = co.car.id
                        WHERE r.id = :rentalId
                        AND (r.user.id = :userId OR c.user.id = :userId)
            """)
    Optional<RentalDetailsProjection> findRentalWithDetails(@Param("rentalId") Long rentalId,
                                                            @Param("userId") Long userId);
    List<Rental> findByUserId(Long userId);
    Optional<Rental> findByCarIdAndStatus(Long carId, RentalStatus status);
}
