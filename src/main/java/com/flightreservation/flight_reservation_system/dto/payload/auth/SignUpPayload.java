package com.flightreservation.flight_reservation_system.dto.payload.auth;

public class SignUpPayload {

    private String token;

    public SignUpPayload(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
