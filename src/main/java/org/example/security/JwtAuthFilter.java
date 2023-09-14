import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;

public class JwtAuthFilter implements jakarta.ws.rs.container.ContainerRequestFilter {
    private final Key key; // Your secret key used for token verification

    public JwtAuthFilter(Key key) {
        this.key = key;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the JWT token from the request headers
        String token = requestContext.getHeaderString("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            // Remove "Bearer " prefix to extract the token
            token = token.substring("Bearer ".length());

            // Verify and parse the token
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract user information from claims (e.g., username)
            String username = claims.getSubject();

            // Create a custom Principal implementation (you may have your own User class)
            Principal principal = new Principal() {
                @Override
                public String getName() {
                    return username;
                }
            };

            // Set the SecurityContext with the authenticated user
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }

                @Override
                public boolean isUserInRole(String role) {
                    // Implement role-based access control logic here if needed
                    return false;
                }

                @Override
                public boolean isSecure() {
                    // Implement secure communication check if needed
                    return requestContext.getUriInfo().getBaseUri().getScheme().equals("https");
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer"; // Authentication scheme (e.g., "Bearer" for JWT)
                }
            });
        }
    }
}
