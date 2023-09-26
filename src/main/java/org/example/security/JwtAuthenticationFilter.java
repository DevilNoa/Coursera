package org.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.net.InetAddress;

@Provider
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private final String secretKey;

    public JwtAuthenticationFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String remoteAddress = requestContext.getHeaders().getFirst("X-Forwarded-For");
        if (remoteAddress == null || InetAddress.getByName(remoteAddress).isLoopbackAddress()) {
            // Request is from localhost, no authentication needed
            return;
        }
        String authorizationHeader = requestContext.getHeaderString("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Barer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        String token = authorizationHeader.substring("Barer ".length()).trim();
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .build().verify(token);

            String userId = decodedJWT.getSubject();

        } catch (JWTVerificationException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

        }
    }
}