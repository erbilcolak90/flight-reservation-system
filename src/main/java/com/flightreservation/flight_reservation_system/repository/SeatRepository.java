package com.flightreservation.flight_reservation_system.repository;

import com.flightreservation.flight_reservation_system.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    boolean existsByFlight_IdAndSeatNumberAndDeletedFalse(Long flightId, int seatNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Seat> findByIdAndDeletedFalse(Long id);

}
