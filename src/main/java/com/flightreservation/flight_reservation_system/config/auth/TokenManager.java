package com.flightreservation.flight_reservation_system.config.auth;

import com.flightreservation.flight_reservation_system.exception.enums.CommonExceptionEnums;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class TokenManager {
    private static final int validity = 7 * 24 * 60 * 60 * 1000;
    private static final Set<String> blacklistedTokens = new HashSet<>();

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String userId = userDetails.getUsername();
        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (role.startsWith("ROLE_")) {
            role = role.substring(5);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    public void logout(String token) {
        expireToken(token);
        SecurityContextHolder.clearContext();
    }

    public void expireToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public boolean tokenValidate(String token) {
        return !isTokenBlacklisted(token) && !isExpired(token);
    }

    public boolean isExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    public String parseUserIdFromToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return (String) body.get("userId");

        } catch (Exception e) {
            throw new RuntimeException(CommonExceptionEnums.INVALID_TOKEN.name());
        }
    }
}

