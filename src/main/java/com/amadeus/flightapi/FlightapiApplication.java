package com.amadeus.flightapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlightapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightapiApplication.class, args);
	}

}
