package com.flightreservation.flight_reservation_system.entity;

import com.flightreservation.flight_reservation_system.enums.ReservationStatusEnums;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatusEnums status;

    public Reservation() {
        super();
    }

    public Reservation(Flight flight, Seat seat, User user, ReservationStatusEnums status) {
        super();
        this.flight = flight;
        this.seat = seat;
        this.user = user;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReservationStatusEnums getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEnums status) {
        this.status = status;
    }
}

