package com.amadeus.flightapi.model.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

public enum UserRole implements GrantedAuthority {

    ADMIN(Constants.ADMIN),
    USER(Constants.USER);

    @Component("ROLES")
    public static class Constants{
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    private final String roleName;

    UserRole(String roleName){
        this.roleName = roleName;
    }
    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
