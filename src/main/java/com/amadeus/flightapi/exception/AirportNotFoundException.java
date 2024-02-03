package com.amadeus.flightapi.exception;

public class AirportNotFoundException extends RuntimeException{
    public AirportNotFoundException(){

    }
    public AirportNotFoundException(String message){
        super(message);
    }
}
