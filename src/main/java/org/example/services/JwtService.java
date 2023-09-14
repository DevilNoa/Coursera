package org.example.services;

import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.core.User;

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

    public boolean validateToken(String token) {
        try {
            // Parse the token
            JWT jwt = JWTParser.parse(token);

            // Create a JWT processor
            ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

            // Set the JWS key selector to null (you can configure it based on your needs)
            jwtProcessor.setJWSKeySelector((jwsHeader, securityContext) -> null);

            // Process the token
            SecurityContext securityContext = new SimpleSecurityContext();
            jwtProcessor.process(jwt, (SimpleSecurityContext) securityContext);

            // If processing was successful, the token is valid
            return true;
        } catch (Exception e) {
            // Token validation failed
            return false;
        }
    }

    public User getUserFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            String password = claims.get("password", String.class); // Extract password from the "password" claim
            String salt = claims.get("salt", String.class); // Extract salt from the "salt" claim
            String email = claims.get("email", String.class); // Extract email from the "email" claim
            String timeCreated = claims.get("timeCreated", String.class); // Extract timeCreated from the "timeCreated" claim

            return new User(username, password, salt, email, timeCreated);
        } catch (Exception e) {
            // Token parsing failed or user information extraction failed
            return null;
        }
    }
}
