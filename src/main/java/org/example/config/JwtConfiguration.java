package org.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class JwtConfiguration extends Configuration {

    @NotEmpty
    private String secretKey;

    @Min(1)
    private int tokenExpirationMinutes;

    public JwtConfiguration() {
    }

    @JsonProperty("secretKey")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

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
