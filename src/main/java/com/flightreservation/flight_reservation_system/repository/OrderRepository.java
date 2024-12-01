package com.flightreservation.flight_reservation_system.repository;

import com.flightreservation.flight_reservation_system.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
