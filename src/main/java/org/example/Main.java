package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class Main {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0X3VzZXJfNSIsImlhdCI6MTY5NTIwMTczNywiZXhwIjoxNjk1MjA1MzM3fQ.zBhoMfE_A2-UB8KD2BMZVdZmki6sy5RvAL8Y_2VGxV8";
        String key = "yourSecretKey";
        try {
            // Parse and verify the JWT token using your secret key
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key) // Use your secret key here
                    .parseClaimsJws(token);

            // Get the claims from the token
            Claims claims = claimsJws.getBody();

            // Extract user information from the claims
            String userId = claims.getSubject();
            // You can access other claims as well, such as expiration, issued at, etc.

            // Now you can check and use the claims as needed
            System.out.println("User ID: " + userId);

        } catch (Exception e) {
            // Handle any exceptions that may occur during token parsing or verification
            e.printStackTrace();
        }
    }
}