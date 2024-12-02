package com.flightreservation.flight_reservation_system.service;

import com.flightreservation.flight_reservation_system.dto.input.flight.GetAllFlightsInput;
import com.flightreservation.flight_reservation_system.dto.payload.flight.FlightPayload;
import com.flightreservation.flight_reservation_system.dto.payload.order.OrderPayload;
import com.flightreservation.flight_reservation_system.dto.payload.reservation.ReservationPayload;
import com.flightreservation.flight_reservation_system.dto.payload.seat.SeatPayload;
import com.flightreservation.flight_reservation_system.dto.payload.user.UserPayload;
import com.flightreservation.flight_reservation_system.entity.*;
import com.flightreservation.flight_reservation_system.exception.enums.*;
import com.flightreservation.flight_reservation_system.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InformationService {

    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public InformationService(FlightRepository flightRepository, SeatRepository seatRepository, OrderRepository orderRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.orderRepository = orderRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public OrderPayload getOrderById(Long id) {
        Order dbOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(OrderExceptionEnums.ORDER_NOT_FOUND.toString()));
        return OrderPayload.convertFromOrder(dbOrder);
    }

    public SeatPayload getSeatById(Long id) {
        Seat dbSeat = seatRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(SeatExceptionEnums.SEAT_NOT_FOUND.toString()));
        return SeatPayload.convertFromSeat(dbSeat);
    }

    public FlightPayload getFlightById(Long id) {
        Flight dbFlight = flightRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(FlightExceptionEnums.FLIGHT_NOT_FOUND.toString()));

        return FlightPayload.convertFromFlight(dbFlight);
    }

    public Page<FlightPayload> getAllFlights(GetAllFlightsInput input) {

        Pageable pageable = PageRequest.of(input.getPageableInput().getNumber(),
                input.getPageableInput().getSize());

        Page<Flight> flightsPage = flightRepository.findAll(pageable);

        return flightsPage.map(FlightPayload::convertFromFlight);
    }

    public ReservationPayload getReservationById(Long id) {

        Reservation dbReservation = reservationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(ReservationExceptionEnums.RESERVATION_NOT_FOUND.toString()));

        return ReservationPayload.convertFromReservation(dbReservation);
    }

    public UserPayload getCurrentUser() {

        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.parseLong(userIdString);

        User dbUser = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(()-> new RuntimeException(UserExceptionEnums.USER_NOT_FOUND.toString()));

        return UserPayload.convertFromUser(dbUser);
    }
}
