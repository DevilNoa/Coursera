package org.example.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtService {
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long expirationTimeMillis = 3600000;

    public static String generateToken(String username) {
        Date now = new Date();
        Date experationDate = new Date(now.getTime() + expirationTimeMillis);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(experationDate)
                .signWith(secretKey)
                .compact();
    }

    public static Claims decodeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
