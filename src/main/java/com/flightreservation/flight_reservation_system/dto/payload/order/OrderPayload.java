package com.flightreservation.flight_reservation_system.dto.payload.order;

import com.flightreservation.flight_reservation_system.entity.Order;

public class OrderPayload {

    private Long id;
    private Long reservationId;

    public OrderPayload(Long id, Long reservationId) {
        this.id = id;
        this.reservationId = reservationId;
    }

    public static OrderPayload convertFromOrder(Order order){
        return new OrderPayload(
                order.getId(),
                order.getReservation().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
