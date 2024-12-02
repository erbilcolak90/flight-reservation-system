package com.flightreservation.flight_reservation_system.dto.payload.auth;

public class LoginPayload {

    private String token;

    public LoginPayload(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
