package com.flightreservation.flight_reservation_system.controller;

import com.flightreservation.flight_reservation_system.dto.input.flight.GetAllFlightsInput;
import com.flightreservation.flight_reservation_system.dto.payload.flight.FlightPayload;
import com.flightreservation.flight_reservation_system.dto.payload.order.OrderPayload;
import com.flightreservation.flight_reservation_system.dto.payload.reservation.ReservationPayload;
import com.flightreservation.flight_reservation_system.dto.payload.seat.SeatPayload;
import com.flightreservation.flight_reservation_system.dto.payload.user.UserPayload;
import com.flightreservation.flight_reservation_system.service.InformationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/information")
@SecurityRequirement(name = "bearer Auth")
public class InformationController {

    private final InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<UserPayload> getCurrentUser(){
        return ResponseEntity.ok(informationService.getCurrentUser());
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<OrderPayload> getOrderById(@RequestParam Long id){
        return ResponseEntity.ok(informationService.getOrderById(id));
    }

    @GetMapping("/getSeatById")
    public ResponseEntity<SeatPayload> getSeatById(@RequestParam Long id){
        return ResponseEntity.ok(informationService.getSeatById(id));
    }

    @GetMapping("/getFlightById")
    public ResponseEntity<FlightPayload> getFlightById(@RequestParam Long id){
        return ResponseEntity.ok(informationService.getFlightById(id));
    }

    @PostMapping("/getAllFlights")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<FlightPayload>> getAllFlights(@RequestBody GetAllFlightsInput getAllFlightsInput){
        return ResponseEntity.ok(informationService.getAllFlights(getAllFlightsInput));
    }

    @GetMapping("/getReservationById")
    public ResponseEntity<ReservationPayload> getReservationById(@RequestParam Long id){
        return ResponseEntity.ok(informationService.getReservationById(id));
    }

}
