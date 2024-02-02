package com.amadeus.flightapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GenericGenerator(name = "airport_id",  strategy = "com.amadeus.flightapi.model.UUIDGenerator")
    @GeneratedValue(generator = "airport_id")
    @Column(name = "id")
    private String id;
    @Column(name = "city")
    private String city;

    public Airport() {
    }

    public Airport(String id, String city) {
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
