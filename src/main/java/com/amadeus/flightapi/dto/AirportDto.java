package com.amadeus.flightapi.dto;


public class AirportDto {
    private String id;
    private String city;
    public AirportDto() {
    }
    public AirportDto(String id, String city) {
        this.id = id;
        this.city = city;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
