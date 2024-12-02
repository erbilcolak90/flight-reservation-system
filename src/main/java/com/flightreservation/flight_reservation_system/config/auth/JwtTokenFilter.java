package com.flightreservation.flight_reservation_system.config.auth;

import com.flightreservation.flight_reservation_system.exception.enums.CommonExceptionEnums;
import com.flightreservation.flight_reservation_system.exception.enums.UserExceptionEnums;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;
    private final SecurityUserDetailsService securityUserDetailsService;

    public JwtTokenFilter(TokenManager tokenManager, SecurityUserDetailsService securityUserDetailsService) {
        this.tokenManager = tokenManager;
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {

                String userIdString = tokenManager.parseUserIdFromToken(jwt);
                Long userId = Long.parseLong(userIdString);
                UserDetails userDetails = securityUserDetailsService.loadUserById(userId);

                if (!tokenManager.tokenValidate(jwt)) {
                    throw new RuntimeException(CommonExceptionEnums.INVALID_TOKEN.name());
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (Exception e) {
            throw new RuntimeException(UserExceptionEnums.USER_NOT_FOUND.name());
        }

        filterChain.doFilter(request, response);
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.getSessionTimeout();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }
}