package com.amadeus.flightapi.repository;

import com.amadeus.flightapi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
}
