package com.flightreservation.flight_reservation_system.dto.payload.user;

import com.flightreservation.flight_reservation_system.entity.User;

public class UserPayload {

    private Long id;
    private String name;
    private String surname;
    private String email;

    public UserPayload(Long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public static UserPayload convertFromUser(User user){
        return new UserPayload(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
