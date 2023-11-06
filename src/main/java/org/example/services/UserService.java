package org.example.services;

import java.sql.SQLException;
import java.util.Set;
import org.example.db.UserDatabase;

public class UserService {
  private final UserDatabase userDatabase;

  // Constructor to initialize UserService with  UserDatabase
  public UserService(UserDatabase userDatabase) {
    this.userDatabase = userDatabase;
  }

  // Method for the creation a new user in the db
  public boolean createUser(
      String username, String hashedPassword, String salt, String email, Set<String> userRole) {
    return userDatabase.createUser(username, hashedPassword, salt, email, userRole);
  }

  // Method retrieving the hashed password of a user
  public String getPasswordHash(String username) throws SQLException {
    return userDatabase.getPasswordHash(username);
  }

  // Method for retrieving the salt of a user
  public String getSalt(String username) throws SQLException {
    return userDatabase.getSalt(username);
  }
}
