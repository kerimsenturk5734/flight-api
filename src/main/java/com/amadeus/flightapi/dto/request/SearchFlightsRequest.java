package com.amadeus.flightapi.dto.request;

import java.time.LocalDate;
import java.util.Optional;

public record SearchFlightsRequest (
        LocalDate goingDate,
        Optional<LocalDate> returnDate,
        String departureCity,
        String landingCity
) {
}
