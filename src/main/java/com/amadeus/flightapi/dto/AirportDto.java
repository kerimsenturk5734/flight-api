package com.amadeus.flightapi.dto;


public class AirportDto {
    private String id;
    private String code;
    private String city;
    public AirportDto() {
    }

    public AirportDto(String id, String code, String city) {
        this.id = id;
        this.code = code;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
