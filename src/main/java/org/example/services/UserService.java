package org.example.services;

import org.example.db.UserDatabase;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Base64;

import static org.example.security.PasswordHashingUtil.hashWithPBKDF2;

public class UserService {
    private final UserDatabase userDatabase;

    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean createUser(String username, String hashedPassword, String salt, String email) {
        return userDatabase.createUser(username, hashedPassword, salt, email);
    }

    public String getPasswordHash(String username) throws SQLException {
        return userDatabase.getPasswordHash(username);
    }

    public String getSalt(String username) throws SQLException {
        return userDatabase.getSalt(username);
    }

    public boolean verifyPassword(String username, String password) throws SQLException {
        String storedHashedPassword = userDatabase.getHashedPasswordByUsername(username);

        if (storedHashedPassword == null) {
            return false;
        }


        String[] parts = storedHashedPassword.split(":");
        if (parts.length != 2) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);


        byte[] hashedPasswordToCheck = hashWithPBKDF2(password.toCharArray(), salt);
        return MessageDigest.isEqual(storedHash, hashedPasswordToCheck);
    }
}
