package com.amadeus.flightapi.dto;

import com.amadeus.flightapi.model.Airport;

import java.time.LocalDateTime;

public class FlightDto {
    String id;
    AirportDto departureAirport;
    AirportDto landingAirport;
    LocalDateTime departureDate;
    Double price;
    public FlightDto() {
    }

    public FlightDto(String id, AirportDto departureAirport, AirportDto landingAirport, LocalDateTime departureDate, Double price) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.landingAirport = landingAirport;
        this.departureDate = departureDate;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AirportDto getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportDto departureAirport) {
        this.departureAirport = departureAirport;
    }

    public AirportDto getLandingAirport() {
        return landingAirport;
    }

    public void setLandingAirport(AirportDto landingAirport) {
        this.landingAirport = landingAirport;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
