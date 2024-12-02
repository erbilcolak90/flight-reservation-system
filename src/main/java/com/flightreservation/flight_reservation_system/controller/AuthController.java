package com.flightreservation.flight_reservation_system.controller;

import com.flightreservation.flight_reservation_system.dto.input.auth.LoginInput;
import com.flightreservation.flight_reservation_system.dto.input.auth.SignUpInput;
import com.flightreservation.flight_reservation_system.dto.payload.auth.LoginPayload;
import com.flightreservation.flight_reservation_system.dto.payload.auth.LogoutPayload;
import com.flightreservation.flight_reservation_system.dto.payload.auth.SignUpPayload;
import com.flightreservation.flight_reservation_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpPayload> signUp(@RequestBody SignUpInput signUpInput) {
        return ResponseEntity.ok(userService.signUp(signUpInput));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginPayload> login(@RequestBody LoginInput loginInput) {
        return ResponseEntity.ok(userService.login(loginInput));
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutPayload> logout(HttpServletRequest request) {
        return ResponseEntity.ok(userService.logout(request));
    }
}
