package com.flightreservation.flight_reservation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FlightReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightReservationSystemApplication.class, args);
	}

}
