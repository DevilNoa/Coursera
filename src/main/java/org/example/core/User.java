package org.example.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String username;
    private String password;
    private String salt;
    private String email;
    private String timeCreated;

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("salt") String salt,
                @JsonProperty("email") String email,
                @JsonProperty("timeCreated") String timeCreated) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.timeCreated = timeCreated;
    }

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

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public static class LoginRequest {
        private final String username;
        private final String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @JsonCreator
        public LoginRequest(@JsonProperty("username") String username
                , @JsonProperty("password") String password) {
            this.username = username;
            this.password = password;

        }
    }
}

