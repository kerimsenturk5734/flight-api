package com.amadeus.flightapi.security;

import com.amadeus.flightapi.exception.ClaimNotFoundException;
import com.amadeus.flightapi.exception.TokenExtractionException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.token.DefaultToken;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class JwtTokenManager {
    private final int VALIDITY = 5*60*100000;
    private final String ISSUER = "com.amadeus.flightapi";
    private final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public Token generate(String username){
        Map<String, Object> claims = new HashMap<>();

        String tokenKey = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+VALIDITY))
                .signWith(KEY)
                .compact();

        return new DefaultToken(tokenKey, new Date().getTime(),"Bearer Jwt Token");
    }

    public boolean validate(String token){
        return !isExpired(token);
    }

    private boolean isExpired(String token){
        Optional<Claims> optionalClaims = extractClaims(token);

        //return false if claims not present else check claims
        return optionalClaims.map(
                claims -> claims.getExpiration().before(new Date(System.currentTimeMillis())))
                .orElse(true);
    }

    public String extractUser(String token){
        Optional<Claims> optionalClaims = extractClaims(token);

        if(optionalClaims.isPresent())
            return optionalClaims.get().getSubject();
        else
            return "";
    }


    private Optional<Claims> extractClaims(String token) {
       try{
           Claims claims = Jwts
                   .parserBuilder()
                   .setSigningKey(KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();

           if(claims.isEmpty())
               throw new ClaimNotFoundException(Jwts.header(), claims, "Claims Not Found By "+token);

           return Optional.of(claims);
       }
       catch (Exception e){
           e.fillInStackTrace();
           return Optional.empty();
       }
    }

    public String extractToken(String authHeader){
        if(authHeader != null && authHeader.startsWith("Bearer ")){

            //Then parse the token from auth header
            //Token starts at the 7th index in an auth header indicated format
            return authHeader.substring(7);
        }

        throw new TokenExtractionException("Invalid token : " + authHeader);
    }
}
