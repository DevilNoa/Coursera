package org.example.security;

import io.jsonwebtoken.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private final String secretKey;

    public JwtAuthenticationFilter(String secret) {
        this.secretKey = secret;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String token = extractTokenFromHeader(requestContext.getHeaderString("Authorization"));

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();


            String userId = claims.getSubject();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {

            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring("Bearer".length()).trim();
        }
        return null;
    }
}