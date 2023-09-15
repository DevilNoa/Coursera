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
    private final UserService userService;
    UserRegistrationRequest userRegistrationRequest;

    public UserResponse(UserService userService) {
        this.userService = userService;
    }

    // Endpoint for user authentication
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User.LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        // Check if the provided username and password are valid
        if (isValidUser(username, password)) {
            // Generate a JWT token for the authenticated user
            String token = JwtService.generateToken(username);
            return Response.ok(new TokenResponse(token)).build();
        } else {
            // Return unauthorized status if authentication fail
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // Helper method to validate user credentials
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

        // Generate a random salt for password hashing
        String salt = PasswordHashingUtil.generateSalt();

        // Hash the user's password using the generated salt
        String hashedPassword = PasswordHashingUtil.hashPassword(password, salt);

        // Create a new user with the hashed password and salt
        boolean success = userService.createUser(username, hashedPassword, salt, email);

        if (success) {
            // Return a success response if user registration is successful
            return Response.status(Response.Status.CREATED).build();
        } else {
            // Return an internal server error response if registration fails
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}