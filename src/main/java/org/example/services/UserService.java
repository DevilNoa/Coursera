package org.example.services;

import org.example.db.UserDatabase;

import java.sql.SQLException;

public class UserService {
    private final UserDatabase userDatabase;

    //Constructor to initialize UserService with  UserDatabase
    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    //Method for the creation a new user in the db
    public boolean createUser(String username, String hashedPassword, String salt, String email) {
        return userDatabase.createUser(username, hashedPassword, salt, email);
    }

    //Method retrieving the hashed password of a user
    public String getPasswordHash(String username) throws SQLException {
        return userDatabase.getPasswordHash(username);
    }

    //Method for retrieving the salt of a user
    public String getSalt(String username) throws SQLException {
        return userDatabase.getSalt(username);
    }
}
