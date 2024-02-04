package com.amadeus.flightapi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        String userId,
        @NotBlank
        String password) {
}
