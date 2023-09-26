package org.example.api;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.db.UserDatabase;

import java.sql.SQLException;

import static org.example.services.JwtService.generateToken;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private final UserDatabase userDatabase;

    public AuthResource(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @POST
    @PermitAll
    @Path("/login")
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            if (userDatabase.verifyPassword(username, password)) {
                // Password is correct, generate and return a JWT token here
                String token = generateToken(username);
                return Response.ok(token).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (SQLException e) {
            // Handle database errors
            return Response.serverError().entity("Database error").build();
        }
    }

}
