package com.flightreservation.flight_reservation_system.service;

import com.flightreservation.flight_reservation_system.dto.input.reservation.CreateReservationInput;
import com.flightreservation.flight_reservation_system.dto.payload.reservation.ReservationPayload;
import com.flightreservation.flight_reservation_system.entity.Flight;
import com.flightreservation.flight_reservation_system.entity.Reservation;
import com.flightreservation.flight_reservation_system.entity.Seat;
import com.flightreservation.flight_reservation_system.entity.User;
import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;
import com.flightreservation.flight_reservation_system.enums.ReservationStatusEnums;
import com.flightreservation.flight_reservation_system.enums.SeatStatusEnums;
import com.flightreservation.flight_reservation_system.exception.enums.FlightExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.ReservationExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.SeatExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.UserExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final OrderRepository orderRepository;

    public ReservationService(ReservationRepository reservationRepository, FlightRepository flightRepository, UserRepository userRepository, SeatRepository seatRepository, OrderRepository orderRepository) {
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public ReservationPayload createReservation(CreateReservationInput createReservationInput) {

        Flight dbFlight = flightRepository.findByIdAndDeletedFalse(createReservationInput.getFlightId())
                .orElseThrow(() -> new RuntimeException(FlightExceptionEnums.FLIGHT_NOT_FOUND.toString()));

        if (!dbFlight.getStatus().equals(FlightStatusEnums.AVAILABLE))
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_STATUS_IS_NOT_AVAILABLE.toString());

        User dbUser = userRepository.findByIdAndDeletedFalse(createReservationInput.getUserId())
                .orElseThrow(() -> new RuntimeException(UserExceptionEnums.USER_NOT_FOUND.toString()));

        Seat dbSeat = seatRepository.findByIdAndDeletedFalse(dbFlight.getId())
                .orElseThrow(() -> new RuntimeException(SeatExceptionEnums.SEAT_NOT_FOUND.toString()));

        if (dbSeat.getStatus().equals(SeatStatusEnums.SOLD))
            throw new RuntimeException(SeatExceptionEnums.SEAT_STATUS_IS_SOLD.toString());

        boolean existsUserAtFlight = reservationRepository.existsByFlight_idAndUser_idAndDeletedFalse(dbFlight.getId(), createReservationInput.getUserId());
        if (existsUserAtFlight)
            throw new RuntimeException(ReservationExceptionEnums.USER_HAS_ALREADY_RESERVATION_AT_THIS_FLIGHT.toString());

        Reservation reservation = new Reservation(
                dbFlight,
                dbSeat,
                dbUser,
                ReservationStatusEnums.WAITING
        );

        reservationRepository.save(reservation);

        return ReservationPayload.convertFromReservation(reservation);
    }

    @Transactional
    public Boolean removeReservation(Long id) {

        Reservation dbReservation = reservationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(ReservationExceptionEnums.RESERVATION_NOT_FOUND.toString()));

        boolean existsOrder = orderRepository.existsByReservation_idAndDeletedFalse(id);
        if (existsOrder) {
            throw new RuntimeException(ReservationExceptionEnums.ORDERED_RESERVATION_CANNOT_REMOVE.toString());
        }

        dbReservation.setDeleted(true);
        dbReservation.setUpdateDate(new Date());
        reservationRepository.save(dbReservation);

        return true;
    }
}
