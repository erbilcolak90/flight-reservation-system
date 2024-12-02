package com.flightreservation.flight_reservation_system.service;

import com.flightreservation.flight_reservation_system.dto.input.flight.AddFlightInput;
import com.flightreservation.flight_reservation_system.dto.input.flight.UpdateFlightInput;
import com.flightreservation.flight_reservation_system.entity.Flight;
import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;
import com.flightreservation.flight_reservation_system.exception.enums.FlightExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.flightreservation.flight_reservation_system.util.Util.parseFlightDate;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Transactional
    public Boolean addFlight(AddFlightInput addFlightInput){

        boolean isExistsFlightName = flightRepository.existsByNameAndDeletedFalse(addFlightInput.getName());
        if (isExistsFlightName) {
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_NAME_IS_ALREADY_EXISTS.toString());
        }

        Date flightDate = parseFlightDate(addFlightInput.getFlightDate());

        if (flightDate.before(new Date())) {
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_DATE_IS_NOT_AVAILABLE.toString());
        }

        Flight flight = new Flight();
        flight.setName(addFlightInput.getName());
        flight.setDescription(addFlightInput.getDescription());
        flight.setFlightDate(flightDate);
        flight.setStatus(FlightStatusEnums.AVAILABLE);
        flightRepository.save(flight);

        return true;
    }

    @Transactional
    public Boolean updateFlight(UpdateFlightInput updateFlightInput){

        Flight dbFlight = flightRepository.findByIdAndDeletedFalse(updateFlightInput.getId())
                .orElseThrow(() -> new RuntimeException(FlightExceptionEnums.FLIGHT_NOT_FOUND.toString()));

        if (dbFlight.getStatus() == FlightStatusEnums.COMPLETED) {
            throw new RuntimeException(FlightExceptionEnums.FLIGHT_STATUS_IS_NOT_AVAILABLE.toString());
        }

        if (updateFlightInput.getName() != null && !dbFlight.getName().equals(updateFlightInput.getName())) {
            boolean existsFlightName = flightRepository.existsByNameAndDeletedFalse(updateFlightInput.getName());
            if (existsFlightName) {
                throw new RuntimeException(FlightExceptionEnums.FLIGHT_NAME_IS_ALREADY_EXISTS.toString());
            }
            dbFlight.setName(updateFlightInput.getName());
        }

        if (updateFlightInput.getFlightDate() != null) {
            Date flightDate = parseFlightDate(updateFlightInput.getFlightDate());

            if (flightDate.before(new Date())) {
                throw new RuntimeException(FlightExceptionEnums.FLIGHT_DATE_IS_NOT_AVAILABLE.toString());
            }

            dbFlight.setFlightDate(flightDate);
        }

        if (updateFlightInput.getDescription() != null) {
            dbFlight.setDescription(updateFlightInput.getDescription());
        }

        dbFlight.setUpdateDate(new Date());
        flightRepository.save(dbFlight);

        return true;
    }

    @Transactional
    public Boolean removeFlight(Long flightId) {

        Flight dbFlight = flightRepository.findByIdAndDeletedFalse(flightId)
                .orElseThrow(() -> new RuntimeException(FlightExceptionEnums.FLIGHT_NOT_FOUND.toString()));

        dbFlight.setDeleted(true);
        dbFlight.setUpdateDate(new Date());

        flightRepository.save(dbFlight);

        return true;
    }
}
