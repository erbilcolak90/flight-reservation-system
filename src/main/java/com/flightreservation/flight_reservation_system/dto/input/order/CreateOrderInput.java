package com.flightreservation.flight_reservation_system.dto.input.order;

public class CreateOrderInput {

    private Long reservationId;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
