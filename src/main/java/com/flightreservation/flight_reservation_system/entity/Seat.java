package com.flightreservation.flight_reservation_system.entity;

import com.flightreservation.flight_reservation_system.enums.SeatStatusEnums;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "seat_number")
    private int seatNumber;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SeatStatusEnums status;

    public Seat() {
        super();
    }

    public Seat(Flight flight, Integer seatNumber, Double price, SeatStatusEnums status) {
        super();
        this.flight = flight;
        this.seatNumber = seatNumber;
        this.price = price;
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

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
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