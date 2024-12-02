package com.flightreservation.flight_reservation_system.repository;

import com.flightreservation.flight_reservation_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDeletedFalse(Long userId);

    boolean existsByIdAndDeletedFalse(Long userId);

    Optional<User> findByEmailAndDeletedFalse(String email);

    boolean existsByEmail(String email);
}
