package com.flightreservation.flight_reservation_system.service;

import com.flightreservation.flight_reservation_system.config.auth.CustomAuthenticationManager;
import com.flightreservation.flight_reservation_system.config.auth.TokenManager;
import com.flightreservation.flight_reservation_system.dto.input.auth.LoginInput;
import com.flightreservation.flight_reservation_system.dto.input.auth.SignUpInput;
import com.flightreservation.flight_reservation_system.dto.payload.auth.LoginPayload;
import com.flightreservation.flight_reservation_system.dto.payload.auth.LogoutPayload;
import com.flightreservation.flight_reservation_system.dto.payload.auth.SignUpPayload;
import com.flightreservation.flight_reservation_system.entity.Role;
import com.flightreservation.flight_reservation_system.entity.User;
import com.flightreservation.flight_reservation_system.exception.enums.CommonExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.UserExceptionEnums;
import com.flightreservation.flight_reservation_system.repository.RoleRepository;
import com.flightreservation.flight_reservation_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;
    private final CustomAuthenticationManager customAuthenticationManager;
    public static final String PASSENGER = "PASSENGER";

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, TokenManager tokenManager, CustomAuthenticationManager customAuthenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Transactional
    public SignUpPayload signUp(SignUpInput signUpInput) {

        boolean existsEmail = userRepository.existsByEmail(signUpInput.getEmail());
        if (existsEmail) {
            throw new RuntimeException(UserExceptionEnums.EMAIL_IS_ALREADY_EXISTS.toString());
        }

        Role dbRole = roleRepository.findByName(PASSENGER).orElseThrow(() -> new RuntimeException(CommonExceptionEnums.ROLE_NOT_FOUND.toString()));

        User user = new User(
                signUpInput.getName(),
                signUpInput.getSurname(),
                signUpInput.getEmail(),
                passwordEncoder.encode(signUpInput.getPassword()),
                dbRole);

        userRepository.save(user);
        String token = tokenManager.generateToken();

        return new SignUpPayload(token);
    }

    public LoginPayload login(LoginInput loginInput) {
        try {
            Authentication authentication = customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginInput.getEmail(),
                    loginInput.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenManager.generateToken();
            return new LoginPayload(token);

        } catch (AuthenticationException e) {
            return new LoginPayload(CommonExceptionEnums.INVALID_EMAIL_OR_PASSWORD.name());
        }
    }

    public LogoutPayload logout(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);

        if (tokenManager.tokenValidate(token)) {
            tokenManager.logout(token);
            SecurityContextHolder.clearContext();
            return new LogoutPayload(HttpStatus.OK.name());

        } else {
            SecurityContextHolder.clearContext();
            return new LogoutPayload(CommonExceptionEnums.INVALID_TOKEN.name());
        }
    }
}
