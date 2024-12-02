package com.flightreservation.flight_reservation_system.entity;

import com.flightreservation.flight_reservation_system.enums.FlightStatusEnums;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatusEnums status;

    @Column(name = "flight_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flightDate;

    public Flight() {
        super();
    }

    public Flight(String name, String description, FlightStatusEnums status, Date flightDate) {
        super();
        this.name = name;
        this.description = description;
        this.status = status;
        this.flightDate = flightDate;
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
