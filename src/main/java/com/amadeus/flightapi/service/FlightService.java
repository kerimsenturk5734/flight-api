package com.amadeus.flightapi.service;

import com.amadeus.flightapi.dto.FlightDto;
import com.amadeus.flightapi.dto.converter.AirportAndAirportDtoConverter;
import com.amadeus.flightapi.dto.converter.FlightAndFlightDtoConverter;
import com.amadeus.flightapi.dto.request.FlightCreateRequest;
import com.amadeus.flightapi.dto.request.SearchFlightsRequest;
import com.amadeus.flightapi.dto.request.UpdateFlightRequest;
import com.amadeus.flightapi.dto.response.OneWaySearchResponse;
import com.amadeus.flightapi.dto.response.TwoWaySearchResponse;
import com.amadeus.flightapi.exception.FlightNotFoundException;
import com.amadeus.flightapi.model.Airport;
import com.amadeus.flightapi.model.Flight;
import com.amadeus.flightapi.repository.FlightRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightAndFlightDtoConverter flightAndFlightDtoConverter;
    private final AirportService airportService;
    private final AirportAndAirportDtoConverter airportAndAirportDtoConverter;
    private final Logger logger = Logger.getLogger(FlightService.class.getName());
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

        logger.info("Flight added...");
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

    @Transactional
    public void addFlightList(List<FlightCreateRequest> flightList){
        logger.info("Flights adding...");
        flightList.forEach(this::add);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    protected void scheduledTest(){
        logger.info("Scheduled test");
        List<FlightCreateRequest> flightCreateRequestList =
                List.of(
                        new FlightCreateRequest(
                                "e376c405-37af-4385-be2b-e69464f5c3d2",
                                "34cd2b8b-b9d6-4edd-be68-cdd03f0fc95a",
                                LocalDateTime.now().plusMonths(1), 1075.60),
                        new FlightCreateRequest(
                                "e376c405-37af-4385-be2b-e69464f5c3d2",
                                "34cd2b8b-b9d6-4edd-be68-cdd03f0fc95a",
                                LocalDateTime.now().plusMonths(1).plusDays(2), 750.12),
                        new FlightCreateRequest(
                                "34cd2b8b-b9d6-4edd-be68-cdd03f0fc95a",
                                "e376c405-37af-4385-be2b-e69464f5c3d2",
                                LocalDateTime.now().plusDays(1), 125.60)
                );

        addFlightList(flightCreateRequestList);
    }

    public OneWaySearchResponse searchFlights(SearchFlightsRequest searchFlightsRequest){
        if(searchFlightsRequest.returnDate().isPresent())
            return searchTwoWayFlights(searchFlightsRequest);
        else
            return searchOneWayFlights(searchFlightsRequest);
    }

    private OneWaySearchResponse searchOneWayFlights(SearchFlightsRequest searchFlightsRequest){
        return new OneWaySearchResponse(
                flightRepository.findFlightsByDepartureDate_DateAndDepartureAirport_CityAndLandingAirport_City(
                        searchFlightsRequest.goingDate(),
                        searchFlightsRequest.departureCity(),
                        searchFlightsRequest.landingCity())
                        .stream()
                        .map(flightAndFlightDtoConverter::convert)
                        .collect(Collectors.toList())
        );
    }
    private TwoWaySearchResponse searchTwoWayFlights(SearchFlightsRequest searchFlightsRequest){
        SearchFlightsRequest returnRequest = reverseFlightRequest(searchFlightsRequest);

        return new TwoWaySearchResponse(
               searchOneWayFlights(searchFlightsRequest).getGoings(),
                searchOneWayFlights(returnRequest).getGoings()
        );
    }

    private SearchFlightsRequest reverseFlightRequest(SearchFlightsRequest searchFlightsRequest){
        return new SearchFlightsRequest(
                searchFlightsRequest.returnDate().get(),
                Optional.of(searchFlightsRequest.goingDate()),
                searchFlightsRequest.landingCity(),
                searchFlightsRequest.departureCity()
        );
    }


}
