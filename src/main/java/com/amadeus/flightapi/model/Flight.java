package com.amadeus.flightapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GenericGenerator(name = "flight_id", strategy = "com.amadeus.flightapi.model.UUIDGenerator")
    @GeneratedValue(generator = "flight_id")
    @Column(name = "id")
    String id;

    @ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id")
    Airport departureAirport;

    @ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "landing_airport_id")
    Airport landingAirport;

    @Column(name = "departure_date", columnDefinition = "TIMESTAMP")
    LocalDateTime departureDate;

    @Column(name = "return_date", columnDefinition = "TIMESTAMP")
    LocalDateTime returnDate;

    public Flight() {
    }

    public Flight(String id, Airport departureAirport, Airport landingAirport, LocalDateTime departureDate, LocalDateTime returnDate) {
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

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getLandingAirport() {
        return landingAirport;
    }

    public void setLandingAirport(Airport landingAirport) {
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
