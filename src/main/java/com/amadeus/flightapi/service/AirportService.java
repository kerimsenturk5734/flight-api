package com.amadeus.flightapi.service;

import com.amadeus.flightapi.dto.AirportDto;
import com.amadeus.flightapi.dto.converter.AirportAndAirportDtoConverter;
import com.amadeus.flightapi.dto.request.AirportCreateRequest;
import com.amadeus.flightapi.dto.request.AirportUpdateRequest;
import com.amadeus.flightapi.exception.AirportAlreadyExistException;
import com.amadeus.flightapi.exception.AirportNotFoundException;
import com.amadeus.flightapi.model.Airport;
import com.amadeus.flightapi.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final AirportAndAirportDtoConverter airportAndAirportDtoConverter;
    public AirportService(AirportRepository airportRepository, AirportAndAirportDtoConverter airportAndAirportDtoConverter) {
        this.airportRepository = airportRepository;
        this.airportAndAirportDtoConverter = airportAndAirportDtoConverter;
    }

    public AirportDto getAirportById(String airportId){
        return airportAndAirportDtoConverter
                .convert(
                        airportRepository
                                .findById(airportId)
                                .orElseThrow(() -> new AirportNotFoundException(
                                                String.format("Airport not found by id : %s", airportId)))
                );
    }

    private Airport getRawAirportById(String airportId){
        return airportRepository
                .findById(airportId)
                .orElseThrow(() -> new AirportNotFoundException(
                        String.format("Airport not found by id : %s", airportId)));
    }

    public List<AirportDto> getAllAirports(){
        return airportRepository
                .findAll()
                .stream()
                .map(airportAndAirportDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public String add(AirportCreateRequest airportCreateRequest){
        return airportRepository
                .save(new Airport(null, airportCreateRequest.code(), airportCreateRequest.city()))
                .getId();
    }

    public String update(String airportId, AirportUpdateRequest airportUpdateRequest){
        Airport airport = getRawAirportById(airportId);

        airport.setCode(airportUpdateRequest.code().orElse(airport.getCode()));
        airport.setCity(airportUpdateRequest.city().orElse(airport.getCity()));

        return airportRepository.save(airport).getId();
    }

    public String delete(String airportId){
        airportRepository.deleteById(getRawAirportById(airportId).getId());
        return airportId;
    }

}
