package org.example.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;


public class User {

    private String username;
    private String passwordHash;
    private String salt;
    private String email;
    private String timeCreated;
    private Set<String> userRole;

    public User() {
    }

    public User(String username, String passwordHash, String salt, String email, String timeCreated, Set<String> userRole) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.email = email;
        this.timeCreated = timeCreated;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public Set<String> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<String> userRole) {
        this.userRole = userRole;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserCredentials {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}