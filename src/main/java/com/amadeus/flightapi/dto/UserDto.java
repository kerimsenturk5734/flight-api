package com.amadeus.flightapi.dto;

import com.amadeus.flightapi.model.enums.UserRole;

public class UserDto {
    String id;
    String name;
    String surname;
    UserRole role;

    public UserDto() {
    }

    public UserDto(String id, String name, String surname, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
