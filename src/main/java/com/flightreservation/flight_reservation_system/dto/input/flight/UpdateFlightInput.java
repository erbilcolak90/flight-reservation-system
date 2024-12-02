package com.flightreservation.flight_reservation_system.dto.input.flight;

import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;

public class UpdateFlightInput {

    private Long id;
    private String name;
    private String description;
    private String flightDate;
    private FlightStatusEnums status;

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

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public FlightStatusEnums getStatus() {
        return status;
    }

    public void setStatus(FlightStatusEnums status) {
        this.status = status;
    }
}


