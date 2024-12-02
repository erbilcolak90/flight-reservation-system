package com.flightreservation.flight_reservation_system.config.auth;

import com.flightreservation.flight_reservation_system.exception.enums.CommonExceptionEnums;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final SecurityUserDetailsService securityUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;


    public CustomAuthenticationManager(SecurityUserDetailsService securityUserDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.securityUserDetailsService = securityUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername(email);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new RuntimeException(CommonExceptionEnums.INVALID_EMAIL_OR_PASSWORD.name());
        }
    }
}