package com.amadeus.flightapi.dto.request;

import jakarta.validation.constraints.Pattern;

import java.util.Optional;

public record UserUpdateRequest(
        Optional<String> name,
        Optional<String> surname,
        Optional<String> password) {

}
