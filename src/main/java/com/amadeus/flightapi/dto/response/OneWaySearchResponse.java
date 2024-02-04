package com.amadeus.flightapi.dto.response;

import com.amadeus.flightapi.dto.FlightDto;

import java.util.List;

public class OneWaySearchResponse {
    List<FlightDto> goings;

    public OneWaySearchResponse(List<FlightDto> goings) {
        this.goings = goings;
    }

    public List<FlightDto> getGoings() {
        return goings;
    }

    public void setGoings(List<FlightDto> goings) {
        this.goings = goings;
    }
}
