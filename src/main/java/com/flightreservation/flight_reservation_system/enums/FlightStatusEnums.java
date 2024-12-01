package com.flightreservation.flight_reservation_system.enums;

public enum FlightStatusEnums {

    AVAILABLE,
    CANCELLED,
    ONBOARDING,
    COMPLETED;

    @Override
    public String toString() {
        return name();
    }
}
