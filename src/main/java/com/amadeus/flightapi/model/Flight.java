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
}
