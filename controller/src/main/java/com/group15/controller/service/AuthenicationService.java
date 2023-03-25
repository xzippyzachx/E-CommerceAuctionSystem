package com.group15.controller.service;

import com.group15.controller.security.JwtUtil;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenicationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    protected final Environment env;
    protected final RestTemplate restTemplate;

    public AuthenicationService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.env = env;
        this.restTemplate = restTemplateBuilder.build();
    }

    public String authenticate(String username, String password) {

        System.out.println(username + " " + password);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            return "Error";
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(userDetails != null) {
            return jwtUtil.generateToken(userDetails);
        }

        return "Error";
    }

    public static record GetUserId(
            String usr_username
    ) {}
    public Integer getUserId(String usr_username) {
        String url = "http://localhost:" + env.getProperty("userServer.port") + "/api/users/get-user-id";

        GetUserId userPayload = new GetUserId(usr_username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", env.getProperty("userServer.apiKey"));
        HttpEntity<GetUserId> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class); //ToDO: Try catch

        return new JSONObject(response.getBody()).getInt("usr_id");
    }
}
