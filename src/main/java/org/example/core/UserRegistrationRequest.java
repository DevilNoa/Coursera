package org.example.core;

public class UserRegistrationRequest {
  private String username;
  private String password;
  private String email;
  private String userRole;

  public UserRegistrationRequest(String username, String password, String email, String userRole) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.userRole = userRole;
  }

  public UserRegistrationRequest() {}

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
