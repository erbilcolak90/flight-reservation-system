package com.flightreservation.flight_reservation_system.dto.payload.flight;

import com.flightreservation.flight_reservation_system.entity.Flight;
import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;

import java.util.Date;

public class FlightPayload {

    private Long id;
    private String name;
    private String description;
    private FlightStatusEnums status;
    private Date flightDate;

    public FlightPayload(Long id, String name, String description, FlightStatusEnums status, Date flightDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.flightDate = flightDate;
    }

    public static FlightPayload convertFromFlight(Flight flight) {
        return new FlightPayload(
                flight.getId(),
                flight.getName(),
                flight.getDescription(),
                flight.getStatus(),
                flight.getFlightDate()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FlightStatusEnums getStatus() {
        return status;
    }

    public void setStatus(FlightStatusEnums status) {
        this.status = status;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }
}
