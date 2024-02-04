package com.amadeus.flightapi.controller;

import com.amadeus.flightapi.dto.FlightDto;
import com.amadeus.flightapi.dto.request.FlightCreateRequest;
import com.amadeus.flightapi.dto.request.SearchFlightsRequest;
import com.amadeus.flightapi.dto.request.UpdateFlightRequest;
import com.amadeus.flightapi.service.FlightService;
import com.amadeus.flightapi.util.SuccessResult;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/v1/api/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @PostMapping("/add")
    public ResponseEntity<?> addAirport(@Valid @RequestBody FlightCreateRequest flightCreateRequest){
        String id = flightService.add(flightCreateRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{flightId}")
    public ResponseEntity<?> getAirportById(@PathVariable String flightId){
        FlightDto flightDto = flightService.getFlightById(flightId);

        return ResponseEntity.ok(flightDto);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllFlights(){
        List<FlightDto> flightDtoList = flightService.getAllFlights();

        return ResponseEntity.ok(flightDtoList);
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @PutMapping("/update/{flightId}")
    public ResponseEntity<?> updateAirport(@PathVariable String flightId,
                                           @Valid @RequestBody UpdateFlightRequest updateFlightRequest){
        String updatedFlightId = flightService.update(flightId, updateFlightRequest);

        return ResponseEntity
                .accepted()
                .body(new SuccessResult(String.format("Flight updated with id : %s", updatedFlightId)));
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @DeleteMapping("/delete/{flightId}")
    public ResponseEntity<?> deleteAirport(@Valid @PathVariable String flightId){
        String deletedFlightId = flightService.delete(flightId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new SuccessResult(String.format("Flight deleted with id : %s", deletedFlightId)));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFlights(SearchFlightsRequest searchFlightsRequest){
        return ResponseEntity.ok().body(flightService.searchFlights(searchFlightsRequest));
    }
}
