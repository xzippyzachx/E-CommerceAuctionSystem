package com.group15.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControllerServer {

	public static void main(String[] args) {
		SpringApplication.run(ControllerServer.class, args);
	}

}
