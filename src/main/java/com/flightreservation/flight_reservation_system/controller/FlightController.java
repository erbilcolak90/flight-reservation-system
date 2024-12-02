package com.flightreservation.flight_reservation_system.controller;

import com.flightreservation.flight_reservation_system.dto.input.flight.AddFlightInput;
import com.flightreservation.flight_reservation_system.dto.input.flight.UpdateFlightInput;
import com.flightreservation.flight_reservation_system.service.FlightService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
@SecurityRequirement(name = "bearer Auth")
@PreAuthorize("hasRole('ADMIN')")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/addFlight")
    public ResponseEntity<Boolean> addFlight(@RequestBody AddFlightInput addFlightInput){
        return ResponseEntity.ok(flightService.addFlight(addFlightInput));
    }

    @PutMapping("/updateFlight")
    public ResponseEntity<Boolean> updateFlight(@RequestBody UpdateFlightInput updateFlightInput){
        return ResponseEntity.ok(flightService.updateFlight(updateFlightInput));
    }

    @DeleteMapping("/removeFlight")
    public ResponseEntity<Boolean> removeFlight(@RequestParam Long id){
        return ResponseEntity.ok(flightService.removeFlight(id));
    }
}
