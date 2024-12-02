package com.flightreservation.flight_reservation_system.util;

import com.flightreservation.flight_reservation_system.entity.Role;
import com.flightreservation.flight_reservation_system.entity.User;
import com.flightreservation.flight_reservation_system.exception.enums.CommonExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.RoleRepository;
import com.flightreservation.flight_reservation_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class SetupUtils {

    private final BCryptPasswordEncoder passwordEncoder;

    public SetupUtils(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean(name = "setupUtilsInit")
    public CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            initializeAdminRole(roleRepository);
            initializeAdminUser(userRepository, roleRepository, passwordEncoder);
        };
    }

    @Transactional
    private void initializeAdminRole(RoleRepository roleRepository) {

        if (!roleRepository.existsByName("ADMIN")) {
            Role adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
        }

        if (!roleRepository.existsByName("USER")) {
            Role userRole = new Role("USER");
            roleRepository.save(userRole);
        }
    }

    @Transactional
    private void initializeAdminUser(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {

        if (!userRepository.existsByEmail("admin")) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException(CommonExceptionEnums.ROLE_NOT_FOUND.toString()));
            User user = new User(
                    "admin",
                    "admin",
                    "admin",
                    passwordEncoder.encode("123123"),
                    adminRole
            );
            userRepository.save(user);
        }

        if (!userRepository.existsByEmail("user")) {
            Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException(CommonExceptionEnums.ROLE_NOT_FOUND.toString()));
            User user = new User(
                    "user",
                    "user",
                    "user",
                    passwordEncoder.encode("123456"),
                    userRole
            );
            userRepository.save(user);
        }
    }
}