package com.group15.controller.security;

import com.group15.controller.service.ControllerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAthFilter extends OncePerRequestFilter {

    private final ControllerService controllerService;
    private final JwtUtil jwtUtil;

    public JwtAthFilter(ControllerService controllerService, JwtUtil jwtUtil) {
        this.controllerService = controllerService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final Cookie cookie = WebUtils.getCookie(request, "access_token");
        final String username;
        final String jwtToken;

        if(authHeader != null && authHeader.startsWith("Bearer")) {
            jwtToken = authHeader.substring(7);
        } else if (cookie != null && !Objects.equals(cookie.getValue(), "")) {
            jwtToken = cookie.getValue();
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtUtil.extractUsername(jwtToken);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = controllerService.getUserDetails(username);
            if(jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
