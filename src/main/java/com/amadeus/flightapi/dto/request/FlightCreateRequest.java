package com.amadeus.flightapi.dto.request;

import java.time.LocalDateTime;

public record FlightCreateRequest(
        String departureAirportId,
        String landingAirportId,
        LocalDateTime departureDate,
        Double price
) {

}
