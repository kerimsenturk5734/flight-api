package com.amadeus.flightapi.controller;

import com.amadeus.flightapi.dto.AirportDto;
import com.amadeus.flightapi.dto.request.AirportCreateRequest;
import com.amadeus.flightapi.dto.request.AirportUpdateRequest;
import com.amadeus.flightapi.service.AirportService;
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
@RequestMapping("/v1/api/airports")
public class AirportController {
    private final AirportService airportService;
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @PostMapping("/add")
    public ResponseEntity<?> addAirport(@Valid @RequestBody AirportCreateRequest airportCreateRequest){
        String id = airportService.add(airportCreateRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{airportId}")
    public ResponseEntity<?> getAirportById(@PathVariable String airportId){
        AirportDto airportDto = airportService.getAirportById(airportId);

        return ResponseEntity.ok(airportDto);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllAirports(){
        List<AirportDto> airportDtoList = airportService.getAllAirports();

        return ResponseEntity.ok(airportDtoList);
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @PutMapping("/update/{airportId}")
    public ResponseEntity<?> updateAirport(@PathVariable String airportId,
                                           @Valid @RequestBody AirportUpdateRequest airportUpdateRequest){
        String updatedAirportId = airportService.update(airportId, airportUpdateRequest);

        return ResponseEntity
                .accepted()
                .body(new SuccessResult(String.format("Airport updated with id : %s", updatedAirportId)));
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @DeleteMapping("/delete/{airportId}")
    public ResponseEntity<?> deleteAirport(@Valid @PathVariable String airportId){
        String deletedAirportId = airportService.delete(airportId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new SuccessResult(String.format("Airport deleted with id : %s", deletedAirportId)));
    }
}
