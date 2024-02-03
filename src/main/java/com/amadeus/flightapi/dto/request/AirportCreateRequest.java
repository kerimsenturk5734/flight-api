package com.amadeus.flightapi.dto.request;

import org.hibernate.validator.constraints.Length;

public record AirportCreateRequest(
        @Length(min = 3, max = 3)
        String code,
        String city
){
}
