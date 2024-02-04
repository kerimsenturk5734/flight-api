package com.amadeus.flightapi.service;

import com.amadeus.flightapi.dto.AirportDto;
import com.amadeus.flightapi.dto.converter.AirportAndAirportDtoConverter;
import com.amadeus.flightapi.dto.request.AirportCreateRequest;
import com.amadeus.flightapi.dto.request.AirportUpdateRequest;
import com.amadeus.flightapi.exception.AirportAlreadyExistException;
import com.amadeus.flightapi.exception.AirportNotFoundException;
import com.amadeus.flightapi.model.Airport;
import com.amadeus.flightapi.model.UUIDGenerator;
import com.amadeus.flightapi.repository.AirportRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final AirportAndAirportDtoConverter airportAndAirportDtoConverter;
    private final FlightService flightService;
    private final Logger logger = Logger.getLogger(AirportService.class.getName());

    public AirportService(AirportRepository airportRepository, AirportAndAirportDtoConverter airportAndAirportDtoConverter,
                          @Lazy FlightService flightService) {
        this.airportRepository = airportRepository;
        this.airportAndAirportDtoConverter = airportAndAirportDtoConverter;
        this.flightService = flightService;
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
                .save(new Airport(UUID.randomUUID().toString(), airportCreateRequest.code(), airportCreateRequest.city()))
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

    private void addAirports(List<Airport> airports){
        airportRepository.saveAll(airports);
    }

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void addDefaultAirportsAndFlights() {
        addAirports(
                List.of(
                        new Airport(
                                "6cec1ec0-19e8-419c-8b6b-1cc2c5f5e4db",
                                "SAW",
                                "ISTANBUL"
                        ),
                        new Airport(
                                "e704fded-4f69-47b8-b31b-640067a00cfc",
                                "KRA",
                                "KRAKOW"
                        ),
                        new Airport(
                                "451ec264-7976-40c0-adda-6ec1d6df70e5",
                                "FCO",
                                "ROME"
                        ),
                        new Airport(
                                "0dadecff-3428-4df2-a9dc-8bdd57aa8c63",
                                "BER",
                                "BERLIN"
                        ),
                        new Airport(
                                "eb1ab68e-ab93-4070-a5be-6cfac4371d18",
                                "VIE",
                                "VIENNA"
                        ),
                        new Airport(
                                "7ac1da29-d66f-440c-8dec-75ff03f606c6",
                                "TBS",
                                "TBILISI"
                        )
                )
        );

        logger.info("Airports added");

        flightService.addDefaultFlights();
    }

}
