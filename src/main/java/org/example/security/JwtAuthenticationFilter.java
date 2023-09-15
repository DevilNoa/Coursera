package org.example.security;

import io.jsonwebtoken.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.net.InetAddress;

@Provider
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private final String secretKey;

    public JwtAuthenticationFilter(String secret) {
        this.secretKey = secret;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Check if the request is coming from localhost
        String remoteAddress = requestContext.getHeaders().getFirst("X-Forwarded-For");
        if (remoteAddress == null || InetAddress.getByName(remoteAddress).isLoopbackAddress()) {
            // Request is from localhost, no authentication needed
            return;
        }

        // Extract the JWT token from the Authorization header
        String token = extractTokenFromHeader(requestContext.getHeaderString("Authorization"));

        try {
            // Parse and verify the JWT token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract user information from the token, if needed
            String userId = claims.getSubject();

        } catch (ExpiredJwtException | UnsupportedJwtException |
                 MalformedJwtException e)// Haling exceptions related to token validation
        {

            //Responding with UNAUTHORIZED status code
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    // Helper method to extract the token from the Authorization header
    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring("Bearer".length()).trim();
        }
        return null;
    }
}