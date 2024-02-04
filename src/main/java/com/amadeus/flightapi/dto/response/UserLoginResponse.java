package com.amadeus.flightapi.dto.response;


import com.amadeus.flightapi.dto.UserDto;
import org.springframework.security.core.token.Token;

public record UserLoginResponse(Token token, UserDto user) {
}
