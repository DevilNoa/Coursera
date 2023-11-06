package org.example.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.example.core.User;
import org.example.core.UserRegistrationRequest;
import org.example.security.PasswordHashingUtil;
import org.example.services.JwtService;
import org.example.services.UserService;

@Path("/user")
public class UserResponse {
  private final UserService userService;

  public UserResponse(UserService userService) {
    this.userService = userService;
  }

  // Endpoint for registering a new user

  @POST
  @Path("/register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response registerUser(UserRegistrationRequest request) {
    String username = request.getUsername();
    String password = request.getPassword();
    String email = request.getEmail();
    String userRole = request.getUserRole(); // Add this line to get the user role

    String salt = PasswordHashingUtil.generateSalt();
    String hashedPassword = PasswordHashingUtil.hashPassword(password, salt);

    // Pass the user role when creating a new user
    Set<String> userRoles = new HashSet<>();
    userRoles.add(userRole);

    boolean success = userService.createUser(username, hashedPassword, salt, email, userRoles);

    if (success) {
      return Response.status(Response.Status.CREATED).build();
    } else {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Endpoint for login and re-thriving the JWT

  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response authenticateUser(User.UserCredentials credentials) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();

    // Check if the provided username and password are valid
    if (isValidUser(username, password)) {
      // Generate a JWT token for the authenticated user
      String token = JwtService.generateToken(username);
      return Response.ok(new TokenResponse(token)).build();
    } else {
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
}
