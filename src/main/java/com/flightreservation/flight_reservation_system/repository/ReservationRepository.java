package com.flightreservation.flight_reservation_system.repository;

import com.flightreservation.flight_reservation_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByFlight_idAndUser_idAndDeletedFalse(Long id, Long userId);

    Optional<Reservation> findByIdAndDeletedFalse(Long id);

    boolean existsByIdAndDeletedFalse(Long reservationId);
}
