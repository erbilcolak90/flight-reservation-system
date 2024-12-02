package com.flightreservation.flight_reservation_system.service;

import com.flightreservation.flight_reservation_system.dto.input.order.CreateOrderInput;
import com.flightreservation.flight_reservation_system.dto.payload.order.OrderPayload;
import com.flightreservation.flight_reservation_system.entity.Order;
import com.flightreservation.flight_reservation_system.entity.Reservation;
import com.flightreservation.flight_reservation_system.entity.Seat;
import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;
import com.flightreservation.flight_reservation_system.enums.ReservationStatusEnums;
import com.flightreservation.flight_reservation_system.enums.SeatStatusEnums;
import com.flightreservation.flight_reservation_system.exception.enums.FlightExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.ReservationExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.SeatExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.OrderRepository;
import com.flightreservation.flight_reservation_system.repository.ReservationRepository;
import com.flightreservation.flight_reservation_system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    public OrderService(OrderRepository orderRepository, ReservationRepository reservationRepository, SeatRepository seatRepository) {
        this.orderRepository = orderRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public OrderPayload createOrder(CreateOrderInput createOrderInput) {

        Reservation dbReservation = reservationRepository.findByIdAndDeletedFalse(createOrderInput.getReservationId())
                .orElseThrow(() -> new RuntimeException(ReservationExceptionEnums.RESERVATION_NOT_FOUND.toString()));

        Seat dbSeat = seatRepository.findByIdAndDeletedFalse(dbReservation.getSeat().getId())
                .orElseThrow(() -> new RuntimeException(SeatExceptionEnums.SEAT_NOT_FOUND.toString()));

        validateReservationAndSeat(dbReservation, dbSeat);

        Order order = new Order(dbReservation);
        orderRepository.save(order);

        updateSeatAndReservation(dbSeat, dbReservation);

        return OrderPayload.convertFromOrder(order);
    }

    private void validateReservationAndSeat(Reservation dbReservation, Seat dbSeat) {

        if (!dbReservation.getFlight().getStatus().equals(FlightStatusEnums.AVAILABLE)) {
            dbReservation.setStatus(ReservationStatusEnums.CANCELLED);
            dbReservation.setUpdateDate(new Date());
            reservationRepository.save(dbReservation);
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_STATUS_IS_NOT_AVAILABLE.toString());
        }

        if (dbReservation.getSeat().getStatus().equals(SeatStatusEnums.SOLD)) {
            dbReservation.setStatus(ReservationStatusEnums.CANCELLED);
            dbReservation.setUpdateDate(new Date());
            reservationRepository.save(dbReservation);
            throw new RuntimeException(SeatExceptionEnums.SEAT_STATUS_IS_SOLD.toString());
        }
    }

    private void updateSeatAndReservation(Seat dbSeat, Reservation dbReservation) {
        Date date = new Date();

        dbSeat.setStatus(SeatStatusEnums.SOLD);
        dbSeat.setUpdateDate(date);
        seatRepository.save(dbSeat);

        dbReservation.setStatus(ReservationStatusEnums.COMPLETED);
        dbReservation.setUpdateDate(date);
        reservationRepository.save(dbReservation);
    }
}
