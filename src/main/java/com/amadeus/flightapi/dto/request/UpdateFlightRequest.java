package com.amadeus.flightapi.dto.request;

import java.time.LocalDateTime;
import java.util.Optional;

public record UpdateFlightRequest(
        Optional<String> departureAirportId,
        Optional<String> landingAirportId,
        Optional<LocalDateTime> departureDate,
        Optional<Double> price
) {
}
