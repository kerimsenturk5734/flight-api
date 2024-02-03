package com.amadeus.flightapi.service;

import com.amadeus.flightapi.dto.FlightDto;
import com.amadeus.flightapi.dto.converter.AirportAndAirportDtoConverter;
import com.amadeus.flightapi.dto.converter.FlightAndFlightDtoConverter;
import com.amadeus.flightapi.dto.request.FlightCreateRequest;
import com.amadeus.flightapi.dto.request.UpdateFlightRequest;
import com.amadeus.flightapi.exception.FlightNotFoundException;
import com.amadeus.flightapi.model.Airport;
import com.amadeus.flightapi.model.Flight;
import com.amadeus.flightapi.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightAndFlightDtoConverter flightAndFlightDtoConverter;
    private final AirportService airportService;
    private final AirportAndAirportDtoConverter airportAndAirportDtoConverter;
    public FlightService(FlightRepository flightRepository, FlightAndFlightDtoConverter flightAndFlightDtoConverter,
                         AirportService airportService, AirportAndAirportDtoConverter airportAndAirportDtoConverter) {
        this.flightRepository = flightRepository;
        this.flightAndFlightDtoConverter = flightAndFlightDtoConverter;
        this.airportService = airportService;
        this.airportAndAirportDtoConverter = airportAndAirportDtoConverter;
    }

    public FlightDto getFlightById(String flightId){
        return flightAndFlightDtoConverter
                .convert(
                        flightRepository
                                .findById(flightId)
                                .orElseThrow(() -> new FlightNotFoundException(
                                        String.format("Flight not found by id : %s", flightId)))
                );
    }

    private Flight getRawFlightById(String flightId){
        return flightRepository
                .findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(
                        String.format("Flight not found by id : %s", flightId)));
    }

    public List<FlightDto> getAllFlights(){
        return flightRepository
                .findAll()
                .stream()
                .map(flightAndFlightDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public String add(FlightCreateRequest flightCreateRequest){
        //Get the airports by id
        Airport departureAirport = airportAndAirportDtoConverter
                .deConvert(airportService.getAirportById(flightCreateRequest.departureAirportId()));

        Airport landingAirport = airportAndAirportDtoConverter
                .deConvert(airportService.getAirportById(flightCreateRequest.landingAirportId()));

        return  flightRepository.save(
                new Flight(
                        null,
                        departureAirport,
                        landingAirport,
                        flightCreateRequest.departureDate(),
                        flightCreateRequest.price()))
                .getId();
    }

    public String update(String flightId, UpdateFlightRequest updateFlightRequest){
        Flight flight = getRawFlightById(flightId);

        if(updateFlightRequest.departureAirportId().isPresent()){
            Airport departureAirport = airportAndAirportDtoConverter
                    .deConvert(airportService.getAirportById(updateFlightRequest.departureAirportId().get()));

            flight.setDepartureAirport(departureAirport);
        }

        if(updateFlightRequest.landingAirportId().isPresent()){
            Airport landingAirport = airportAndAirportDtoConverter
                    .deConvert(airportService.getAirportById(updateFlightRequest.landingAirportId().get()));

            flight.setLandingAirport(landingAirport);
        }

        flight.setDepartureDate(updateFlightRequest.departureDate().orElse(flight.getDepartureDate()));
        flight.setPrice(updateFlightRequest.price().orElse(flight.getPrice()));

        return flightRepository.save(flight).getId();
    }

    public String delete(String flightId){
        Flight flight = getRawFlightById(flightId);
        flightRepository.deleteById(flightId);

        return flightId;
    }
}
