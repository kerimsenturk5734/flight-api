package com.amadeus.flightapi.dto.converter;

import com.amadeus.flightapi.dto.FlightDto;
import com.amadeus.flightapi.model.Flight;
import com.amadeus.flightapi.util.ObjectConverter.Convertable;

public class FlightAndFlightDtoConverter implements Convertable<Flight, FlightDto> {
    private final AirportAndAirportDtoConverter airportAndAirportDtoConverter;

    public FlightAndFlightDtoConverter(AirportAndAirportDtoConverter airportAndAirportDtoConverter) {
        this.airportAndAirportDtoConverter = airportAndAirportDtoConverter;
    }

    @Override
    public FlightDto convert(Flight flight) {
        if(flight == null)
            return null;

        return new FlightDto(
                flight.getId(),
                airportAndAirportDtoConverter.convert(flight.getDepartureAirport()),
                airportAndAirportDtoConverter.convert(flight.getLandingAirport()),
                flight.getDepartureDate(),
                flight.getReturnDate());
    }

    @Override
    public Flight deConvert(FlightDto flightDto) {
        if(flightDto == null)
            return null;

        return new Flight(
                flightDto.getId(),
                airportAndAirportDtoConverter.deConvert(flightDto.getDepartureAirport()),
                airportAndAirportDtoConverter.deConvert(flightDto.getLandingAirport()),
                flightDto.getDepartureDate(),
                flightDto.getReturnDate());
    }
}
