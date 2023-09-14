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