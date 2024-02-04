package com.amadeus.flightapi.dto.response;

import com.amadeus.flightapi.dto.FlightDto;

import java.util.List;

public class TwoWaySearchResponse extends OneWaySearchResponse{
    List<FlightDto> turns;

    public TwoWaySearchResponse(List<FlightDto> goings, List<FlightDto> turns) {
        super(goings);
        this.turns = turns;
    }

    public List<FlightDto> getTurns() {
        return turns;
    }

    public void setTurns(List<FlightDto> turns) {
        this.turns = turns;
    }
}
