package com.flightreservation.flight_reservation_system.controller;

import com.flightreservation.flight_reservation_system.dto.input.seat.AddSeatToFlightInput;
import com.flightreservation.flight_reservation_system.dto.input.seat.UpdateSeatFromFlightInput;
import com.flightreservation.flight_reservation_system.service.SeatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
@SecurityRequirement(name = "bearer Auth")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/addSeatToFlight")
    public ResponseEntity<Boolean> addSeatToFlight(@RequestBody AddSeatToFlightInput addSeatToFlightInput){
        return ResponseEntity.ok(seatService.addSeatToFlight(addSeatToFlightInput));
    }

    @PutMapping("/updateSeatFromFlight")
    public ResponseEntity<Boolean> updateSeatFromFlight(@RequestBody UpdateSeatFromFlightInput updateSeatFromFlightInput){
        return ResponseEntity.ok(seatService.updateSeatFromFlight(updateSeatFromFlightInput));
    }

    @DeleteMapping("/removeSeatFromFlight")
    public ResponseEntity<Boolean> removeSeatFromFlight(@RequestParam Long id){
        return ResponseEntity.ok(seatService.removeSeatFromFlight(id));
    }
}
