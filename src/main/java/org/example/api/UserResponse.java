package org.example.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.User;
import org.example.services.JwtService;
import org.example.services.UserService;

import java.sql.SQLException;

@Path("/auth")
public class UserResponse {
    private final UserService userService;

    public UserResponse(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User.LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (isValidUser(username, password)) {
            String token = JwtService.generateToken(username);
            return Response.ok(new TokenResponse(token)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean isValidUser(String username, String password) {
        try {
            String storedPasswordHash = userService.getPasswordHash(username);
            String salt = userService.getSalt(username);
            String hashedPassword = hashPassword(password, salt);

            return storedPasswordHash.equals(hashedPassword);
        } catch (SQLException e) {
            return false;
        }
    }

    private String hashPassword(String password, String salt) {

        return password;
    }
}

