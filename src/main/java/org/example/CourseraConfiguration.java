package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import org.example.config.JwtConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CourseraConfiguration extends Configuration {
    private final Connection connection;

    private JwtConfiguration jwtConfiguration;

    public CourseraConfiguration() {
        try {
            String jdbcURL = "jdbc:postgresql://localhost:5433/postgres";
            String username = "postgres";
            String password = "admin";
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database server.");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database server.");
            throw new RuntimeException(e);
        }
    }

    @JsonProperty("jwtConfiguration")
    public JwtConfiguration getJwtConfiguration() {
        return jwtConfiguration;
    }


    public String getJwtSecretKey() {
        return jwtConfiguration.getSecretKey();
    }

    public long getTokenExpirationMinutes() {
        return jwtConfiguration.getTokenExpirationMinutes();
    }

    public Connection getConnection() {
        return connection;
    }
}