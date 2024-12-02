package com.flightreservation.flight_reservation_system.config.auth;


import com.flightreservation.flight_reservation_system.entity.User;
import com.flightreservation.flight_reservation_system.exception.enums.UserExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User dbUser = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException(UserExceptionEnums.USER_NOT_FOUND.name()));

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_"+ dbUser.getRole().getName()));

        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getId().toString())
                .password(dbUser.getPassword())
                .authorities(authorities)
                .build();
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

        User dbUser = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException(UserExceptionEnums.USER_NOT_FOUND.name()));

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + dbUser.getRole().getName()));

        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getId().toString())
                .password(dbUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
