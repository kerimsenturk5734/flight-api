package com.amadeus.flightapi.exception;

public class AirportAlreadyExistException extends RuntimeException{
    public AirportAlreadyExistException() {
    }

    public AirportAlreadyExistException(String message) {
        super(message);
    }
}
