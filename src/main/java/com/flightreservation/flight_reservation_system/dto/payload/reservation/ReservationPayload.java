package com.flightreservation.flight_reservation_system.dto.payload.reservation;

import com.flightreservation.flight_reservation_system.entity.Reservation;
import com.flightreservation.flight_reservation_system.enums.ReservationStatusEnums;

import java.util.Date;

public class ReservationPayload {

    private Long id;
    private Long flightId;
    private Long userId;
    private Long seatId;
    private ReservationStatusEnums status;
    private Date createDate;

    public ReservationPayload(Long id, Long flightId, Long userId, Long seatId, ReservationStatusEnums status, Date createDate) {
        this.id = id;
        this.flightId = flightId;
        this.userId = userId;
        this.seatId = seatId;
        this.status = status;
        this.createDate = createDate;
    }

    public static ReservationPayload convertFromReservation(Reservation reservation){

        return new ReservationPayload(
                reservation.getId(),
                reservation.getFlight().getId(),
                reservation.getUser().getId(),
                reservation.getSeat().getId(),
                reservation.getStatus(),
                reservation.getCreateDate()
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public ReservationStatusEnums getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEnums status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
