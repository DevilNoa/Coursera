package org.example.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.User;
import org.example.core.UserRegistrationRequest;
import org.example.security.PasswordHashingUtil;
import org.example.services.JwtService;
import org.example.services.UserService;

import java.sql.SQLException;

@Path("/user")
public class UserResponse {
    UserRegistrationRequest userRegistrationRequest;
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

            // Hash the user's input password using your utility method
            String hashedPassword = PasswordHashingUtil.hashPassword(password, salt);

            return storedPasswordHash.equals(hashedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hashPassword(String password, String salt) {

        return password;
    }

    @POST
    @Path("/register")
    public Response registerUser(UserRegistrationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        String salt = PasswordHashingUtil.generateSalt();

        String hashedPassword = PasswordHashingUtil.hashPassword(password, salt);

        boolean success = userService.createUser(username, hashedPassword, salt, email);

        if (success) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}