package com.amadeus.flightapi.repository;

import com.amadeus.flightapi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    default List<Flight> findFlightsByDepartureDate_DateAndDepartureAirport_CityAndLandingAirport_City(
            LocalDate departureDate_date, String departureAirport_city, String landingAirport_city){

        return findFlightsByDepartureDateBetweenAndDepartureAirport_CityAndLandingAirport_City(
                departureDate_date.atStartOfDay(),
                departureDate_date.plusDays(1).atStartOfDay(),
                departureAirport_city,
                landingAirport_city
        );
    }
    List<Flight> findFlightsByDepartureDateBetweenAndDepartureAirport_CityAndLandingAirport_City(
            LocalDateTime startDateTime, LocalDateTime endDateTime,
            String departureAirport_city, String landingAirport_city);
}
