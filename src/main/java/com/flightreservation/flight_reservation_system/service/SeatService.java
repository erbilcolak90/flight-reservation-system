package com.flightreservation.flight_reservation_system.service;

import com.flightreservation.flight_reservation_system.dto.input.seat.AddSeatToFlightInput;
import com.flightreservation.flight_reservation_system.dto.input.seat.UpdateSeatFromFlightInput;
import com.flightreservation.flight_reservation_system.entity.Flight;
import com.flightreservation.flight_reservation_system.entity.Seat;
import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;
import com.flightreservation.flight_reservation_system.enums.SeatStatusEnums;
import com.flightreservation.flight_reservation_system.exception.enums.FlightExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.SeatExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.FlightRepository;
import com.flightreservation.flight_reservation_system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private static final double MINIMUM_SEAT_PRICE = 0.1;

    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    @Transactional
    public Boolean addSeatToFlight(AddSeatToFlightInput addSeatToFlightInput) {

        Flight dbFlight = flightRepository.findByIdAndDeletedFalse(addSeatToFlightInput.getFlightId())
                .orElseThrow(() -> new RuntimeException(FlightExceptionEnums.FLIGHT_NOT_FOUND.toString()));

        if (!dbFlight.getStatus().equals(FlightStatusEnums.AVAILABLE))
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_STATUS_IS_NOT_AVAILABLE.toString());

        boolean isExistsSeatNumber = seatRepository.existsByFlight_IdAndSeatNumberAndDeletedFalse(dbFlight.getId(), addSeatToFlightInput.getSeatNumber());

        if (isExistsSeatNumber)
            throw new RuntimeException(SeatExceptionEnums.SEAT_NUMBER_IS_ALREADY_EXISTS_IN_FLIGHT.toString());

        if (addSeatToFlightInput.getPrice() < MINIMUM_SEAT_PRICE)
            throw new RuntimeException(SeatExceptionEnums.PRICE_CANNOT_LESS_THAN_MINIMUM_SEAT_PRICE.toString());

        Seat seat = new Seat(
                dbFlight,
                addSeatToFlightInput.getSeatNumber(),
                addSeatToFlightInput.getPrice(),
                SeatStatusEnums.AVAILABLE
        );

        seatRepository.save(seat);

        return true;
    }

    @Transactional
    public Boolean updateSeatFromFlight(UpdateSeatFromFlightInput updateSeatFromFlightInput) {

        Seat dbSeat = seatRepository.findByIdAndDeletedFalse(updateSeatFromFlightInput.getSeatId())
                .orElseThrow(() -> new RuntimeException(SeatExceptionEnums.SEAT_NOT_FOUND.toString()));

        Flight dbFlight = dbSeat.getFlight();

        if (!dbFlight.getStatus().equals(FlightStatusEnums.AVAILABLE))
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_STATUS_IS_NOT_AVAILABLE.toString());


        if (!dbSeat.getSeatNumber().equals(updateSeatFromFlightInput.getSeatNumber())) {

            boolean isSeatNumberAvailable = seatRepository.existsByFlight_IdAndSeatNumberAndDeletedFalse(
                    dbFlight.getId(), updateSeatFromFlightInput.getSeatNumber());

            if (isSeatNumberAvailable && !dbSeat.getStatus().equals(SeatStatusEnums.SOLD)) {
                dbSeat.setSeatNumber(updateSeatFromFlightInput.getSeatNumber());
            } else if (dbSeat.getStatus().equals(SeatStatusEnums.SOLD)) {
                throw new RuntimeException(SeatExceptionEnums.SEAT_STATUS_IS_SOLD.toString());
            }
        }

        if (updateSeatFromFlightInput.getPrice() >= MINIMUM_SEAT_PRICE) {
            dbSeat.setPrice(updateSeatFromFlightInput.getPrice());
        }

        dbSeat.setUpdateDate(new Date());
        seatRepository.save(dbSeat);

        return true;
    }

    @Transactional
    public Boolean removeSeatFromFlight(Long id) {

        Seat dbSeat = seatRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(SeatExceptionEnums.SEAT_NOT_FOUND.toString()));

        Flight dbFlight = dbSeat.getFlight();

        if(!dbFlight.getStatus().equals(FlightStatusEnums.AVAILABLE))
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_STATUS_IS_NOT_AVAILABLE.toString());

        dbSeat.setDeleted(true);
        dbSeat.setUpdateDate(new Date());
        seatRepository.save(dbSeat);

        return true;
    }
}