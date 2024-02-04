package com.amadeus.flightapi.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(){

    }
    public FlightNotFoundException(String message){
        super(message);
    }
}
