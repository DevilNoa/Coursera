package org.example.db;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Set;


public class UserDatabase {
    private final Connection connection;

    public UserDatabase(Connection connection) {
        this.connection = connection;
    }

    //method for creating a user in the db
    public boolean createUser(String username, String hashedPassword, String salt, String email, Set<String> userRoles) {
        String sql = "INSERT INTO users (username, password_hash, salt, email, user_role) VALUES (?, ?, ?, ?, ?)";
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
    //Method to retrieve password hash from user
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

    //Method to retrieve salt hash from user
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

    // Method to verify a password by comparing it with the stored hashed password
    public boolean verifyPassword(String username, String password) throws SQLException {
        String storedHashedPassword = getHashedPasswordByUsername(username);

        if (storedHashedPassword == null) {
            return false;
        }

        String[] parts = storedHashedPassword.split(":");
        if (parts.length != 3) {
            return false;
        }

        String salt = parts[0];
        String hashedPassword = parts[1];
        int iterations = Integer.parseInt(parts[2]);

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        byte[] hashedPasswordBytes = Base64.getDecoder().decode(hashedPassword);

        // Verify the password using PBKDF2
        return verifyPasswordPBKDF2(password, saltBytes, hashedPasswordBytes, iterations);
    }

    // Method to verify a password using PBKDF2 with HMAC-SHA256
    private boolean verifyPasswordPBKDF2(String password, byte[] salt, byte[] hashedPassword, int iterations) {
        char[] passwordChars = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, hashedPassword.length * 8);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] testHashedPassword = factory.generateSecret(spec).getEncoded();

            // Compare the computed hash with the stored hash
            return MessageDigest.isEqual(hashedPassword, testHashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Handle exceptions (e.g., log the error)
            return false;
        }
    }

    // Method to retrieve the hashed password from the database by username
    public String getHashedPasswordByUsername(String username) throws SQLException {
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
}
