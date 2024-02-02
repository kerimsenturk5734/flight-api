package com.amadeus.flightapi.dto;

import com.amadeus.flightapi.model.Airport;

import java.time.LocalDateTime;

public class FlightDto {
    String id;
    AirportDto departureAirport;
    AirportDto landingAirport;
    LocalDateTime departureDate;
    LocalDateTime returnDate;

    public FlightDto() {
    }

    public FlightDto(String id, AirportDto departureAirport, AirportDto landingAirport, LocalDateTime departureDate, LocalDateTime returnDate) {
        this.id = id;
        this.departureAirport = departureAirport;
        this.landingAirport = landingAirport;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
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

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
