package com.amadeus.flightapi.model;

import com.amadeus.flightapi.model.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "user_id", strategy = "com.amadeus.flightapi.model.UUIDGenerator")
    @GeneratedValue(generator = "user_id")
    String id;
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @Column(name = "password")
    String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    UserRole role;

    public User() {
    }

    public User(String id, String name, String surname, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return this.id;
    }

    public void setUserId(String id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
