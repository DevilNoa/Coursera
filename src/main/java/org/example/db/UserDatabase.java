package org.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class UserDatabase {
  private final Connection connection;

  public UserDatabase(Connection connection) {
    this.connection = connection;
  }

  // method for creating a user in the db
  public boolean createUser(
      String username, String hashedPassword, String salt, String email, Set<String> userRoles) {
    String sql =
        "INSERT INTO users (username, password_hash, salt, email, user_role) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      statement.setString(2, hashedPassword);
      statement.setString(3, salt);
      statement.setString(4, email);

      // Convert userRoles Set to a comma-separated string
      String userRolesString = String.join(",", userRoles);
      statement.setString(5, userRolesString);

      int rowsInserted = statement.executeUpdate();
      return rowsInserted > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Method to retrieve password hash from user
  public String getPasswordHash(String username) throws SQLException {
    String sql = "SELECT password_hash FROM users WHERE username = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString("password_hash");
        }
      }
    }
    return null;
  }

  // Method to retrieve salt hash from user
  public String getSalt(String username) throws SQLException {
    String sql = "SELECT salt FROM users WHERE username = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString("salt");
        }
      }
    }
    return null;
  }
}
