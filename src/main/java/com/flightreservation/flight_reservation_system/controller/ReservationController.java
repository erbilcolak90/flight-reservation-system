package com.flightreservation.flight_reservation_system.controller;

import com.flightreservation.flight_reservation_system.dto.input.reservation.CreateReservationInput;
import com.flightreservation.flight_reservation_system.dto.payload.reservation.ReservationPayload;
import com.flightreservation.flight_reservation_system.service.ReservationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@SecurityRequirement(name = "bearer Auth")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/createReservation")
    public ResponseEntity<ReservationPayload> createReservation(@RequestBody CreateReservationInput createReservationInput){
        return ResponseEntity.ok(reservationService.createReservation(createReservationInput));
    }

    @DeleteMapping("/removeReservation")
    public ResponseEntity<Boolean> removeReservation(@RequestParam Long id){
        return ResponseEntity.ok(reservationService.removeReservation(id));
    }
}
