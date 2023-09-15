package org.example.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private final String username;
    private final String password;
    private final String salt;
    private final String email;
    private final String timeCreated;

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

    public String getTimeCreated() {
        return timeCreated;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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

