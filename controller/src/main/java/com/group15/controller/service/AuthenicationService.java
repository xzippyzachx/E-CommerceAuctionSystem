package com.group15.controller.service;

import com.group15.controller.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenicationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenicationService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String username, String password) {

        System.out.println(username + " " + password);

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails != null) {
            return jwtUtil.generateToken(userDetails);
        }

        return "Error";
    }
}
