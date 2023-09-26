package org.example.core;

public class UserRegistrationRequest {
    private final String username;
    private final String password;
    private final String email;
    private final String userRole;

    public UserRegistrationRequest(String username, String password, String email, String userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserRole() {
        return userRole;
    }

}