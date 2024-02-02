package com.amadeus.flightapi.dto.converter;

import com.amadeus.flightapi.dto.AirportDto;
import com.amadeus.flightapi.model.Airport;
import com.amadeus.flightapi.util.ObjectConverter.Convertable;

public class AirportAndAirportDtoConverter implements Convertable<Airport, AirportDto> {
    @Override
    public AirportDto convert(Airport airport) {
        if(airport == null)
            return null;

        return new AirportDto(airport.getId(), airport.getCity());
    }

    @Override
    public Airport deConvert(AirportDto airportDto) {
        if(airportDto == null)
            return null;

        return new Airport(airportDto.getId(), airportDto.getCity());
    }
}
