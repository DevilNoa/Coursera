package org.example.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtService {

    //Defining a secret key for using the JWT token
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //JWT expiration
    private static final long expirationTimeMillis = 3600000;

    // Method to generate a JWT token for a given username
    public static String generateToken(String username) {

        // Get the current date and time
        Date now = new Date();

        // Calculate the expiration date by adding the expiration time to the current time
        Date experationDate = new Date(now.getTime() + expirationTimeMillis);

        // Build and sign the JWT token with the specified claims and secret key
        return Jwts.builder()
                .setSubject(username)           //Set user
                .setIssuedAt(now)               //Set the date/time
                .setExpiration(experationDate)  //Set expiration token time
                .signWith(secretKey)            //Sign the token with the security key
                .compact();                     //Retitling the final form of the token
    }
}
