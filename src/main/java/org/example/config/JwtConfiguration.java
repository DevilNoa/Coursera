package org.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class JwtConfiguration extends Configuration {

    // Field to store the JWT secret key
    @NotEmpty
    private String secretKey;

    // Field to store the JWT token expiration time in minutes
    @Min(1)
    private int tokenExpirationMinutes;

    public JwtConfiguration() {
    }

    // Setter method for the secretKey field (used for deserialization)
    @JsonProperty("secretKey")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    // Setter method for the tokenExpirationMinutes field (used for deserialization)
    @JsonProperty("tokenExpirationMinutes")
    public void setTokenExpirationMinutes(int tokenExpirationMinutes) {
        this.tokenExpirationMinutes = tokenExpirationMinutes;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public int getTokenExpirationMinutes() {
        return tokenExpirationMinutes;
    }
}
