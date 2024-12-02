package com.flightreservation.flight_reservation_system.dto.payload.seat;

import com.flightreservation.flight_reservation_system.entity.Seat;
import com.flightreservation.flight_reservation_system.enums.SeatStatusEnums;

public class SeatPayload {

    private Long id;
    private Long flightId;
    private int seatNumber;
    private Double price;
    private SeatStatusEnums status;

    public SeatPayload(Long id, Long flightId, int seatNumber, Double price, SeatStatusEnums status) {
        this.id = id;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    public static SeatPayload convertFromSeat(Seat seat){
        return new SeatPayload(
                seat.getId(),
                seat.getFlight().getId(),
                seat.getSeatNumber(),
                seat.getPrice(),
                seat.getStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SeatStatusEnums getStatus() {
        return status;
    }

    public void setStatus(SeatStatusEnums status) {
        this.status = status;
    }
}
