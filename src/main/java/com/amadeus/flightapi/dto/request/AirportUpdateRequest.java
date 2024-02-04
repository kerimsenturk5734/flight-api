package com.amadeus.flightapi.dto.request;

import java.util.Optional;

public record AirportUpdateRequest(
        Optional<String> code,
        Optional<String> city
) {

}
