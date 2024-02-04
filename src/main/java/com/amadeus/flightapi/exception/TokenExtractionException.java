package com.amadeus.flightapi.exception;

public class TokenExtractionException extends RuntimeException {
    public TokenExtractionException() {
    }

    public TokenExtractionException(String message) {
        super(message);
    }
}
